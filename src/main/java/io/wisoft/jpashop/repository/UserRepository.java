package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.user.Email;
import io.wisoft.jpashop.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(Email email);
}
