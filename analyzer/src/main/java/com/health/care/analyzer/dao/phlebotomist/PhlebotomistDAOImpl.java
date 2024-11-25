package com.health.care.analyzer.dao.phlebotomist;

import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PhlebotomistDAOImpl implements PhlebotomistDAO {
    private final EntityManager entityManager;

    @Autowired
    public PhlebotomistDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Phlebotomist save(Phlebotomist phlebotomist) {
        entityManager.persist(phlebotomist);
        return phlebotomist;
    }

    @Override
    public Optional<Phlebotomist> findByUser(User user) {
        TypedQuery<Phlebotomist> query = entityManager.createQuery("from Phlebotomist p where p.user = :user",
                Phlebotomist.class);
        query.setParameter("user", user);
        List<Phlebotomist> phlebotomistList = query.getResultList();
        if(phlebotomistList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(phlebotomistList.get(0));
    }

    @Override
    public void update(Phlebotomist phlebotomist) {
        entityManager.createQuery("update Phlebotomist p set p.dob = :dob, p.weight = :weight, p.height = :height, " +
                "p.phoneNo = :phoneNo, p.bloodGroup = :bloodGroup where p.user = :user")
                .setParameter("dob", phlebotomist.getDob())
                .setParameter("weight", phlebotomist.getWeight())
                .setParameter("height", phlebotomist.getHeight())
                .setParameter("phoneNo", phlebotomist.getPhoneNo())
                .setParameter("bloodGroup", phlebotomist.getBloodGroup())
                .setParameter("user", phlebotomist.getUser())
                .executeUpdate();
    }
}
