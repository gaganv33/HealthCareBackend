package com.health.care.analyzer.dao.user;

import com.health.care.analyzer.entity.userEntity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);
    User save(User user);
    List<User> getAllUser();
    List<User> getAllAdmin();
    List<User> getAllDoctor();
    List<User> getAllPatient();
    List<User> getAllReceptionist();
    List<User> getAllPhlebotomist();
    List<User> getAllMedicineVendor();
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
    List<User> getAllEnabledMedicineVendor();
    List<User> getAllDisabledMedicineVendor();
    void enableUser(String username);
    void disableUser(String username);
}
