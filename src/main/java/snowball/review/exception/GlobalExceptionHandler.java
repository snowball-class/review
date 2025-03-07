package snowball.review.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import snowball.review.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ReviewNotFoundException.class})
    public ResponseEntity<ApiResponse> handleLessonNotFoundException(ReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("해당하는 리뷰가 없습니다."));
    }

    public static class ReviewNotFoundException extends RuntimeException{
        public ReviewNotFoundException() {
            super();
        }
    }
}