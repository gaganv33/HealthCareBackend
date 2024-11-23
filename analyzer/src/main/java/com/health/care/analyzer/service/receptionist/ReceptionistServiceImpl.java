package com.health.care.analyzer.service.receptionist;

import com.health.care.analyzer.dao.receptionist.ReceptionistDAO;
import com.health.care.analyzer.entity.userEntity.Receptionist;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceptionistServiceImpl implements ReceptionistService {
    private final ReceptionistDAO receptionistDAO;

    @Autowired
    public ReceptionistServiceImpl(ReceptionistDAO receptionistDAO) {
        this.receptionistDAO = receptionistDAO;
    }

    @Override
    @Transactional
    public void save(Receptionist receptionist) {
        Optional<Receptionist> receptionistOptional = receptionistDAO.findByUser(receptionist.getUser());
        if(receptionistOptional.isEmpty()) {
            receptionistDAO.save(receptionist);
        } else {
            receptionistDAO.update(receptionist);
        }
    }
}
