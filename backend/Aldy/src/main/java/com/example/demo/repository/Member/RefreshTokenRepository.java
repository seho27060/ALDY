package com.example.demo.repository.Member;

import com.example.demo.domain.entity.Member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByBaeckjoonId(String baeckjoonId);
    void deleteByBaeckjoonId(String baeckjoonId);
}
