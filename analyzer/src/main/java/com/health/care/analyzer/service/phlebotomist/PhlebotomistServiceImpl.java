package com.health.care.analyzer.service.phlebotomist;

import com.health.care.analyzer.dao.phlebotomist.PhlebotomistDAO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhlebotomistServiceImpl implements PhlebotomistService {
    private final PhlebotomistDAO phlebotomistDAO;

    @Autowired
    public PhlebotomistServiceImpl(PhlebotomistDAO phlebotomistDAO) {
        this.phlebotomistDAO = phlebotomistDAO;
    }

    @Override
    @Transactional
    public void save(Phlebotomist phlebotomist) {
        Optional<Phlebotomist> phlebotomistOptional = phlebotomistDAO.findByUser(phlebotomist.getUser());
        if(phlebotomistOptional.isEmpty()) {
            phlebotomistDAO.save(phlebotomist);
        } else {
            phlebotomistDAO.update(phlebotomist);
        }
    }

    @Override
    public ProfileResponseDTO getPhlebotomistProfile(User user) {
        Phlebotomist phlebotomist = phlebotomistDAO.getPhlebotomistProfile(user);
        return ProfileResponseDTO.builder()
                .dob(phlebotomist.getDob())
                .phoneNo(phlebotomist.getPhoneNo())
                .bloodGroup(phlebotomist.getBloodGroup())
                .weight(phlebotomist.getWeight())
                .height(phlebotomist.getHeight())
                .build();
    }
}
