package com.health.care.analyzer.service.admin;

import com.health.care.analyzer.dao.admin.AdminDAO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Admin;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO;

    @Autowired
    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    @Transactional
    public void save(Admin admin) {
        Optional<Admin> adminOptional = adminDAO.findByUser(admin.getUser());
        if(adminOptional.isEmpty()) {
            adminDAO.save(admin);
        } else {
            adminDAO.update(admin);
        }
    }

    @Override
    public ProfileResponseDTO getAdminProfile(User user) {
        Admin admin = adminDAO.getAdminProfile(user);
        return ProfileResponseDTO.builder()
                .dob(admin.getDob())
                .phoneNo(admin.getPhoneNo())
                .bloodGroup(admin.getBloodGroup())
                .weight(admin.getWeight())
                .height(admin.getHeight())
                .build();
    }
}
