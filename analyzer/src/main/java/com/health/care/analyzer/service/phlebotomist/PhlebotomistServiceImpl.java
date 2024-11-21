package com.health.care.analyzer.service.phlebotomist;

import com.health.care.analyzer.dao.phlebotomist.PhlebotomistDAO;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
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
}
