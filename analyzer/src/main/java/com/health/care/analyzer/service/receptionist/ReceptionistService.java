package com.health.care.analyzer.service.receptionist;

import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Receptionist;
import com.health.care.analyzer.entity.userEntity.User;

public interface ReceptionistService {
    void save(Receptionist receptionist);
    public ProfileResponseDTO getReceptionistProfile(User user);
}
