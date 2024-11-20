package com.health.care.analyzer.service.auth;

import com.health.care.analyzer.dao.user.UserDAO;
import com.health.care.analyzer.dto.auth.UserInfoDetails;
import com.health.care.analyzer.entity.userEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    @Autowired
    public UserInfoDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        return new UserInfoDetails(user.get());
    }
}
