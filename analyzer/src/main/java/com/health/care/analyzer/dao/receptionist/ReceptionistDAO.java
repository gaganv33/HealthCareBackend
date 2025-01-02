package com.health.care.analyzer.dao.receptionist;

import com.health.care.analyzer.entity.userEntity.Receptionist;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.Optional;

public interface ReceptionistDAO {
    Receptionist save(Receptionist receptionist);
    Optional<Receptionist> findByUser(User user);
    void update(Receptionist receptionist);
    public Receptionist getReceptionistProfile(User user);
}
