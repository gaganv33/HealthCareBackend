package com.health.care.analyzer.service.user;

import com.health.care.analyzer.dto.doctor.UserProfileResponseDTO;
import com.health.care.analyzer.dto.user.UserResponseDTO;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.authentication.UsernameAlreadyTakenException;

import java.util.List;

public interface UserService {
    User save(User user) throws UsernameAlreadyTakenException;
    User findByUsername(String username);
    List<UserResponseDTO> getAllUser();
    List<UserResponseDTO> getAllAdmin();
    List<UserResponseDTO> getAllDoctor();
    List<UserResponseDTO> getAllPatient();
    List<UserResponseDTO> getAllReceptionist();
    List<UserResponseDTO> getAllPhlebotomist();
    List<UserResponseDTO> getAllMedicineVendor();
    List<UserResponseDTO> getEnabledUser();
    List<UserResponseDTO> getDisabledUser();
    List<UserResponseDTO> getAllEnabledAdmin();
    List<UserResponseDTO> getAllDisabledAdmin();
    List<UserResponseDTO> getAllEnabledDoctor();
    List<UserResponseDTO> getAllDisabledDoctor();
    List<UserResponseDTO> getAllEnabledPatient();
    List<UserResponseDTO> getAllDisabledPatient();
    List<UserResponseDTO> getAllEnabledReceptionist();
    List<UserResponseDTO> getAllDisabledReceptionist();
    List<UserResponseDTO> getAllEnabledPhlebotomist();
    List<UserResponseDTO> getAllDisabledPhlebotomist();
    List<UserResponseDTO> getAllEnabledMedicineVendor();
    List<UserResponseDTO> getAllDisabledMedicineVendor();
    void enableUser(String username);
    void disableUser(String username);
    List<String> getEnabledDoctorUsername();
    List<UserProfileResponseDTO> getEnabledDoctorProfile();
    User getUserUsingAuthorizationHeader(String authorization);
}
