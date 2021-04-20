package io.wisoft.jpashop.service;

import com.google.common.base.Preconditions;
import io.wisoft.jpashop.domain.user.Email;
import io.wisoft.jpashop.domain.user.User;
import io.wisoft.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User login(Email email, String password) {
        return findByEmail(email)
                .map(user -> {
                    user.login(password);
                    user.afterLoginSuccess();
                    return user;
                })
                .orElseThrow(() -> new IllegalArgumentException("Could not login to " + email));
    }
    
    public Optional<User> findByEmail(Email email) {
        checkArgument(email != null, "email must be required.");
        return userRepository.findByEmail(email);
    }
}
