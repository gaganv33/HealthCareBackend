package com.health.care.analyzer.service.user;

import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.UsernameAlreadyTakenException;

import java.util.List;

public interface UserService {
    User save(User user) throws UsernameAlreadyTakenException;
    User findByUsername(String username);
    List<User> getAllUser();
    List<User> getAllAdmin();
    List<User> getAllDoctor();
    List<User> getAllPatient();
    List<User> getAllReceptionist();
    List<User> getAllPhlebotomist();
    List<User> getEnabledUser();
    List<User> getDisabledUser();
    List<User> getAllEnabledAdmin();
    List<User> getAllDisabledAdmin();
    List<User> getAllEnabledDoctor();
    List<User> getAllDisabledDoctor();
    List<User> getAllEnabledPatient();
    List<User> getAllDisabledPatient();
    List<User> getAllEnabledReceptionist();
    List<User> getAllDisabledReceptionist();
    List<User> getAllEnabledPhlebotomist();
    List<User> getAllDisabledPhlebotomist();
    void enableUser(String username);
    void disableUser(String username);
    void deleteUser(String username);
}
