package com.health.care.analyzer.service.auth.refreshToken;

import com.health.care.analyzer.dao.auth.refreshToken.RefreshTokenDAO;
import com.health.care.analyzer.dao.user.UserDAO;
import com.health.care.analyzer.entity.RefreshToken;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.InvalidRefreshTokenException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserDAO userDAO;
    private final RefreshTokenDAO refreshTokenDAO;

    @Autowired
    public RefreshTokenServiceImpl(UserDAO userDAO, RefreshTokenDAO refreshTokenDAO) {
        this.userDAO = userDAO;
        this.refreshTokenDAO = refreshTokenDAO;
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(String username) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user.get())
                .token(UUID.randomUUID().toString())
                .expiry(Instant.now().plusMillis(600000))
                .build();
        refreshTokenDAO.deleteTokenByUser(user.get());
        return refreshTokenDAO.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenDAO.findByToken(token);
    }

    @Override
    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) throws InvalidRefreshTokenException {
        if(token.getExpiry().compareTo(Instant.now()) < 0) {
            Optional<RefreshToken> refreshToken = refreshTokenDAO.findByToken(token.getToken());
            if(refreshToken.isPresent()) {
                refreshTokenDAO.deleteByToken(token.getToken());
                throw new InvalidRefreshTokenException("Invalid refresh token");
            }
        }
        return token;
    }

    @Override
    @Transactional
    public void deleteTokenByUser(String username) {
        Optional<User> user = userDAO.findByUsername(username);
        if(user.isEmpty()) {
            return;
        }
        refreshTokenDAO.deleteTokenByUser(user.get());
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        refreshTokenDAO.deleteByToken(token);
    }
}
