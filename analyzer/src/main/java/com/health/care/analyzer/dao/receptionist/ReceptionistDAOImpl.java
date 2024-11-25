package com.health.care.analyzer.dao.receptionist;

import com.health.care.analyzer.entity.userEntity.Receptionist;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReceptionistDAOImpl implements ReceptionistDAO {
    private final EntityManager entityManager;

    @Autowired
    public ReceptionistDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Receptionist save(Receptionist receptionist) {
        entityManager.persist(receptionist);
        return receptionist;
    }

    @Override
    public Optional<Receptionist> findByUser(User user) {
        TypedQuery<Receptionist> query = entityManager.createQuery("from Receptionist r where r.user = :user",
                Receptionist.class);
        query.setParameter("user", user);
        List<Receptionist> receptionistList = query.getResultList();
        if(receptionistList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(receptionistList.get(0));
    }

    @Override
    public void update(Receptionist receptionist) {
        entityManager.createQuery("update Receptionist r set r.dob = :dob, r.weight = :weight, r.height = height, " +
                "r.phoneNo = :phoneNo, r.bloodGroup = :bloodGroup where r.user = :user")
                .setParameter("dob", receptionist.getDob())
                .setParameter("weight", receptionist.getWeight())
                .setParameter("height", receptionist.getHeight())
                .setParameter("phoneNo", receptionist.getPhoneNo())
                .setParameter("bloodGroup", receptionist.getBloodGroup())
                .setParameter("user", receptionist.getUser())
                .executeUpdate();
    }
}
