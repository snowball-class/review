package snowball.review.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        int status,
        String message,
        T data

){
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), null, data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(HttpStatus.OK.value(), null, null);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), message, null);
    }
}