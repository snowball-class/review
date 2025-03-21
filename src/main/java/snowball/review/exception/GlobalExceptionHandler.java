package snowball.review.exception;

import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<ApiResponse> handleReviewNotFoundException(ReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("해당하는 리뷰가 없습니다."));
    }

    @ExceptionHandler({DuplicateReviewException.class})
    public ResponseEntity<ApiResponse> handleDuplicateReviewException(DuplicateReviewException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("이미 해당 수업에 대한 리뷰가 존재합니다."));
    }

    public static class ReviewNotFoundException extends RuntimeException{
        public ReviewNotFoundException() {
            super();
        }
    }

    public static class DuplicateReviewException extends RuntimeException{
        public DuplicateReviewException(){
            super();
        }
    }
}