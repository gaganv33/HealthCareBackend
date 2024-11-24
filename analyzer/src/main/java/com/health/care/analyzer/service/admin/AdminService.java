package com.health.care.analyzer.service.admin;

import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Admin;
import com.health.care.analyzer.entity.userEntity.User;

public interface AdminService {
    void save(Admin admin);
    ProfileResponseDTO getAdminProfile(User user);
}
