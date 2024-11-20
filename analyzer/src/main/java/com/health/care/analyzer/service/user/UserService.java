package com.health.care.analyzer.service.user;

import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.UsernameAlreadyTakenException;

public interface UserService {
    User save(User user) throws UsernameAlreadyTakenException;
    User findByUsername(String username);
}
