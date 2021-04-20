package io.wisoft.jpashop.domain.user;

import io.wisoft.jpashop.domain.BaseTimeEntity;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.hasText;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Embedded
    private Email email;

    @Column(name = "password")
    private String password;

    @Column(name = "login_count")
    private int loginCount;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    protected User() {}

    private User(String name, Email email, String password) {
        /* 첫번째 매개변수(표현식)가 거짓이라면 에러메시지 반환 */
        checkArgument(hasText(name), "name must be required.");
        checkArgument(email != null, "email must be required.");
        checkArgument(hasText(password), "password must be required.");
        checkArgument(password.length() >= 6 && password.length() <= 16, "password's length must be between 6 and 16 characters.");

        this.name = name;
        this.email = email;
        this.password = passwordEncoding(password);
        this.loginCount = 0;
        this.lastLoginAt = null;
    }

    public static User from(String name, Email email, String password) {
        return new User(name, email, password);
    }

    private String passwordEncoding(String password) {
        /* Base64 통해 인코딩된 결과를 다시 sha512 암호화 알고리즘을 통해 인코딩. */
        return Base64.encodeBase64String(DigestUtils.sha512(password));
    }

    public void login(String password) {
        if (!hasText(password) || !passwordEncoding(password).equals(this.password)) {
            throw new IllegalStateException("Login failed.");
        }
    }

    public void afterLoginSuccess() {
        loginCount++;
        lastLoginAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email=" + email +
                ", password='" + "[PROTECTED]" + '\'' +
                ", loginCount=" + loginCount +
                ", lastLoginAt=" + lastLoginAt +
                '}';
    }
}
