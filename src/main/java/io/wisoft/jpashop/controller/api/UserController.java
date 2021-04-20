package io.wisoft.jpashop.controller.api;

import io.wisoft.jpashop.domain.user.Email;
import io.wisoft.jpashop.domain.user.User;
import io.wisoft.jpashop.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static io.wisoft.jpashop.controller.api.ApiResult.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ApiResult<UserResponse> login(@RequestBody LoginRequest request) {
        return succeed(
                new UserResponse(
                        userService.login(Email.from(request.getEmailAddress()), request.getPassword())
                )
        );
    }

    @Getter
    @ToString
    private static class LoginRequest {

        private String emailAddress;
        private String password;

        protected LoginRequest() {
        }

        public LoginRequest(String emailAddress, String password) {
            this.emailAddress = emailAddress;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @ToString
    private static class UserResponse {

        private Long id;
        private String name;
        private Email email;
        private int loginCount;
        private LocalDateTime lastLoginAt;
        private LocalDateTime createAt;

        protected UserResponse() {
        }

        public UserResponse(User source) {
            BeanUtils.copyProperties(source, this);
            this.lastLoginAt = source.getLastLoginAt();
        }
    }
}
