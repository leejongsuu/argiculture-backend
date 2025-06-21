package com.aivle.agriculture.domain.Login.repository;
import com.aivle.agriculture.domain.Login.entity.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 조회하는 메서드 추가도 고려
    Optional<User> findByEmail(String email);

}
