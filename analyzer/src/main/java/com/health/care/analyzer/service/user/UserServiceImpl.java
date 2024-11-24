package com.health.care.analyzer.service.user;

import com.health.care.analyzer.dao.user.UserDAO;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.UsernameAlreadyTakenException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }

    @Override
    public List<User> getAllAdmin() {
        return userDAO.getAllAdmin();
    }

    @Override
    public List<User> getAllDoctor() {
        return userDAO.getAllDoctor();
    }

    @Override
    public List<User> getAllPatient() {
        return userDAO.getAllPatient();
    }

    @Override
    public List<User> getAllReceptionist() {
        return userDAO.getAllReceptionist();
    }

    @Override
    public List<User> getAllPhlebotomist() {
        return userDAO.getAllPhlebotomist();
    }

    @Override
    public List<User> getEnabledUser() {
        return userDAO.getEnabledUser();
    }

    @Override
    public List<User> getDisabledUser() {
        return userDAO.getDisabledUser();
    }

    @Override
    public List<User> getAllEnabledAdmin() {
        return userDAO.getAllEnabledAdmin();
    }

    @Override
    public List<User> getAllDisabledAdmin() {
        return userDAO.getAllDisabledAdmin();
    }

    @Override
    public List<User> getAllEnabledDoctor() {
        return userDAO.getAllEnabledDoctor();
    }

    @Override
    public List<User> getAllDisabledDoctor() {
        return userDAO.getAllDisabledDoctor();
    }

    @Override
    public List<User> getAllEnabledPatient() {
        return userDAO.getAllEnabledPatient();
    }

    @Override
    public List<User> getAllDisabledPatient() {
        return userDAO.getAllDisabledPatient();
    }

    @Override
    public List<User> getAllEnabledReceptionist() {
        return userDAO.getAllEnabledReceptionist();
    }

    @Override
    public List<User> getAllDisabledReceptionist() {
        return userDAO.getAllDisabledReceptionist();
    }

    @Override
    public List<User> getAllEnabledPhlebotomist() {
        return userDAO.getAllEnabledPhlebotomist();
    }

    @Override
    public List<User> getAllDisabledPhlebotomist() {
        return userDAO.getAllDisabledPhlebotomist();
    }

    @Override
    @Transactional
    public void enableUser(String username) {
        userDAO.enableUser(username);
    }

    @Override
    @Transactional
    public void disableUser(String username) {
        userDAO.disableUser(username);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        userDAO.deleteUser(username);
    }
}
