package toyProject.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snowball.review.dto.review.reviewRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewRequest.ReviewUpdateRequest;
import snowball.review.repository.ReviewRepository;
import snowball.review.review.Review;
import snowball.review.service.ReviewService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewTests {

	@Mock
	private ReviewRepository reviewRepository;
	private ReviewService reviewService;
	// 테스트용 dummy secret 값
	private final String dummySecret = "dummySecretValue";

	@BeforeEach
	public void setUp() {
		// 수동으로 ReviewService 생성
		reviewService = new ReviewService(reviewRepository, dummySecret);
	}

	@Test
	void testCreateReview() {
		//given
		ReviewCreateRequest request = new ReviewCreateRequest();
		request.setReviewContent("리뷰컨텐트");
		request.setStarScore(3.5);
		request.setLessonId(5L);

		Long lessonId = 8L;
		String accessToken = "Bearer accessToken";

		UUID memberUUID = UUID.randomUUID();

		// TokenParsing 구현
		ReviewService spy = spy(reviewService);
		doReturn(memberUUID).when(spy).tokenParser(accessToken);

		// reviewRepository.save() 호출 시, 저장된 리뷰에 ID를 부여하도록 설정
		when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> {
			Review review = invocation.getArgument(0);
			review.setReviewId(1L); // 저장 후 생성된 ID 설정
			return review;
		});

		// when
		Long returnedReviewId = spy.createReview(request, lessonId, accessToken);

		// then: 실제로 reviewRepository.save()에 전달된 Review 객체를 캡처
		ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
		verify(reviewRepository).save(reviewCaptor.capture());
		Review savedReview = reviewCaptor.getValue();

		assertEquals("리뷰컨텐트", savedReview.getReviewContent());
		assertEquals(lessonId, savedReview.getLessonId()); // 메서드 내부에서 lessonId 파라미터가 사용됨
		assertEquals(3.5, savedReview.getStarScore());
		assertEquals(memberUUID, savedReview.getMemberUUID());
		assertEquals(1L, returnedReviewId);
	}

	@Test
	void testUpdateReview() {
		//given : update 후 새 객체
		String accessToken = "";
		ReviewUpdateRequest request = new ReviewUpdateRequest();
		request.setReviewContent("Update Review");
		request.setStarScore(3.0);

		//update 전 기존 객체
		Review review = new Review();
		Long reviewId = 15L;
		review.setReviewId(reviewId);
		review.setReviewContent("Original Review");
		review.setStarScore(5.0);

		when(reviewRepository.findByReviewId(reviewId)).thenReturn(Optional.of(review));

		//when
		Long returnReviewId = reviewService.updateReview(request, reviewId, accessToken);

		//then
		assertEquals(reviewId, returnReviewId);
		assertEquals("Update Review", review.getReviewContent());
		assertEquals(3.0, review.getStarScore());

		verify(reviewRepository, times(1)).findByReviewId(reviewId);
	}

	@Test
	void testDeleteReview() {

	}

}
