package com.health.care.analyzer.dao.auth.refreshToken;

import com.health.care.analyzer.entity.RefreshToken;
import com.health.care.analyzer.entity.users.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RefreshTokenDAOImpl implements RefreshTokenDAO {
    private final EntityManager entityManager;

    @Autowired
    public RefreshTokenDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        TypedQuery<RefreshToken> query = entityManager.createQuery("from RefreshToken rf where rf.token = :token", RefreshToken.class);
        query.setParameter("token", token);
        List<RefreshToken> refreshTokenList = query.getResultList();
        if(refreshTokenList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(refreshTokenList.get(0));
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        entityManager.persist(refreshToken);
        return refreshToken;
    }

    @Override
    public void deleteByToken(String token) {
        entityManager.createQuery("delete from RefreshToken rf where rf.token = :token")
                .setParameter("token", token)
                .executeUpdate();
    }

    @Override
    public void deleteTokenByUser(User user) {
        entityManager.createQuery("delete from RefreshToken rf where rf.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }

    @Override
    public Optional<RefreshToken> findByUser(User user) {
        TypedQuery<RefreshToken> query = entityManager.createQuery("from RefreshToken rf where rf.user = :user", RefreshToken.class);
        query.setParameter("user", user);
        List<RefreshToken> refreshTokenList = query.getResultList();
        if(refreshTokenList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(refreshTokenList.get(0));
    }
}
