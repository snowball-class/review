package snowball.review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import snowball.review.dto.ApiResponse;
import snowball.review.dto.member.MemberInfoResponse;

@FeignClient(name = "member-service", url = "${feign.snowball.member}")
public interface MemberClient {
    @GetMapping("/member")
    ApiResponse<MemberInfoResponse> getMemberInfo(@RequestHeader("Authorization") String token);
}
