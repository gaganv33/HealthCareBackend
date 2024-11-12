package com.health.care.analyzer.service.auth.refreshToken;

import com.health.care.analyzer.entity.RefreshToken;
import com.health.care.analyzer.exception.InvalidRefreshTokenException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username) throws UsernameNotFoundException;
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token) throws InvalidRefreshTokenException;
    void deleteTokenByUser(String username);
}
