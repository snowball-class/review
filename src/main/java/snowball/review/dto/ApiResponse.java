package snowball.review.dto;

import org.springframework.http.HttpStatus;

// feign에서 오면서 HttpStatus로 받으면서 200이 오류남
// Jackson은 기본적으로 HttpStatus를 Enum으로 처리
// 숫자로 들어오면 변환할 방법을 몰라서 오류 발생
// 예) ApiResponse<MemberInfoResponse> 타입으로 응답을 받는데,
// HttpStatus status 필드의 값이 200(숫자)로 들어와서 역직렬화 오류

public record ApiResponse<T>(
        HttpStatus status,
        String message,
        T data

){
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK, null, data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(HttpStatus.OK, null, null);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, message, null);
    }

//    // JSON 요청에서 숫자로 된 상태 코드도 변환할 수 있도록 설정, 설정 필요
//    @JsonCreator
//    public static HttpStatus fromCode(int statusCode) {
//        return HttpStatus.resolve(statusCode); // 200 -> HttpStatus.OK 변환
//    }
}