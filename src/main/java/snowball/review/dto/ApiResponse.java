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
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

//    // JSON 요청에서 숫자로 된 상태 코드도 변환할 수 있도록 설정, 디팬던시 필요
//    @JsonCreator
//    public static HttpStatus fromCode(int statusCode) {
//        return HttpStatus.resolve(statusCode); // 200 -> HttpStatus.OK 변환
//    }
}