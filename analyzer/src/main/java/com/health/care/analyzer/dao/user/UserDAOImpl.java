package com.health.care.analyzer.dao.user;

import com.health.care.analyzer.entity.users.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> userList = query.getResultList();
        if(userList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userList.get(0));
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
}
