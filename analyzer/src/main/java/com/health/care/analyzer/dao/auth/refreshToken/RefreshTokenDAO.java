package com.health.care.analyzer.dao.auth.refreshToken;

import com.health.care.analyzer.entity.RefreshToken;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.Optional;

public interface RefreshTokenDAO {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken save(RefreshToken refreshToken);
    void deleteByToken(String token);
    void deleteTokenByUser(User user);
    Optional<RefreshToken> findByUser(User user);
}
