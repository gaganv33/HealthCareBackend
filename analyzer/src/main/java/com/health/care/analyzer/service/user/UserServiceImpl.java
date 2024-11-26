package com.health.care.analyzer.service.user;

import com.health.care.analyzer.dao.user.UserDAO;
import com.health.care.analyzer.dto.doctor.UserProfileResponseDTO;
import com.health.care.analyzer.dto.user.UserResponseDTO;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.UsernameAlreadyTakenException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<UserResponseDTO> getAllUser() {
        return userDAO.getAllUser().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllAdmin() {
        return userDAO.getAllAdmin().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllDoctor() {
        return userDAO.getAllDoctor().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllPatient() {
        return userDAO.getAllPatient().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllReceptionist() {
        return userDAO.getAllReceptionist().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllPhlebotomist() {
        return userDAO.getAllPhlebotomist().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getEnabledUser() {
        return userDAO.getEnabledUser().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getDisabledUser() {
        return userDAO.getDisabledUser().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllEnabledAdmin() {
        return userDAO.getAllEnabledAdmin().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllDisabledAdmin() {
        return userDAO.getAllDisabledAdmin().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllEnabledDoctor() {
        return userDAO.getAllEnabledDoctor().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllDisabledDoctor() {
        return userDAO.getAllDisabledDoctor().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllEnabledPatient() {
        return userDAO.getAllEnabledPatient().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllDisabledPatient() {
        return userDAO.getAllDisabledPatient().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllEnabledReceptionist() {
        return userDAO.getAllEnabledReceptionist().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllDisabledReceptionist() {
        return userDAO.getAllDisabledReceptionist().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllEnabledPhlebotomist() {
        return userDAO.getAllEnabledPhlebotomist().stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public List<UserResponseDTO> getAllDisabledPhlebotomist() {
        return userDAO.getAllDisabledPhlebotomist().stream().map(UserResponseDTO::new).toList();
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
    public List<String> getEnabledDoctorUsername() {
        List<User> doctorList = userDAO.getAllEnabledDoctor();
        return doctorList.stream().map(User::getUsername).toList();
    }

    @Override
    public List<UserProfileResponseDTO> getEnabledDoctorProfile() {
        List<User> doctorList = userDAO.getAllEnabledDoctor();
        return doctorList.stream().map(doctor -> UserProfileResponseDTO.builder()
                .username(doctor.getUsername())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName()).build()).toList();
    }
}
