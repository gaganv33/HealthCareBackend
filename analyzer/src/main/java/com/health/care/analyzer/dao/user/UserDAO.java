package com.health.care.analyzer.dao.user;

import com.health.care.analyzer.entity.users.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);
    User save(User user);
}
