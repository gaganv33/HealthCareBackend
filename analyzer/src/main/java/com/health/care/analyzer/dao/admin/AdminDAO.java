package com.health.care.analyzer.dao.admin;

import com.health.care.analyzer.entity.userEntity.Admin;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.Optional;

public interface AdminDAO {
    Admin save(Admin admin);
    Optional<Admin> findByUser(User user);
    void update(Admin admin);
    Admin getAdminProfile(User user);
}
