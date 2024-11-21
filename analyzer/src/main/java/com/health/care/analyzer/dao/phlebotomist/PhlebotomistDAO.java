package com.health.care.analyzer.dao.phlebotomist;

import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.Optional;

public interface PhlebotomistDAO {
    Phlebotomist save(Phlebotomist phlebotomist);
    Optional<Phlebotomist> findByUser(User user);
    void update(Phlebotomist phlebotomist);
}
