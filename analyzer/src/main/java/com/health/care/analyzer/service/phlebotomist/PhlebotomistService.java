package com.health.care.analyzer.service.phlebotomist;

import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import com.health.care.analyzer.entity.userEntity.User;

public interface PhlebotomistService {
    void save(Phlebotomist phlebotomist);
    ProfileResponseDTO getPhlebotomistProfile(User user);
}
