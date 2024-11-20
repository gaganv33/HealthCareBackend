package com.health.care.analyzer.service.user;

import com.health.care.analyzer.dao.user.UserDAO;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.UsernameAlreadyTakenException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User save(User user) throws UsernameAlreadyTakenException {
        Optional<User> userDatabase = userDAO.findByUsername(user.getUsername());
        if(userDatabase.isPresent()) {
            throw new UsernameAlreadyTakenException("Username " + user.getUsername() + " already taken.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userDAO.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user.get();
    }
}
