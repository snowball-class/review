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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler({DuplicateReviewException.class})
    public ResponseEntity<ApiResponse> handleDuplicateReviewException(DuplicateReviewException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
    }

    public static class ReviewNotFoundException extends RuntimeException{
        public ReviewNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateReviewException extends RuntimeException{
        public DuplicateReviewException(String message){
            super(message);
        }
    }
}