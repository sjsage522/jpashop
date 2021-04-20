package io.wisoft.jpashop.controller;

import io.wisoft.jpashop.domain.user.Email;
import io.wisoft.jpashop.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api")
public class UserController {



    @Getter
    @ToString
    private static class LoginRequest {

        private String emailAddress;
        private String password;

        protected LoginRequest() {}

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

        protected UserResponse() {}

        public UserResponse(User source) {
            BeanUtils.copyProperties(source, this);
            this.lastLoginAt = source.getLastLoginAt();
        }
    }
}
