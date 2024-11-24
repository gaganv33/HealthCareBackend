package com.health.care.analyzer.dao.admin;

import com.health.care.analyzer.entity.userEntity.Admin;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminDAOImpl implements AdminDAO {
    private final EntityManager entityManager;

    @Autowired
    public AdminDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Admin save(Admin admin) {
        entityManager.persist(admin);
        return admin;
    }

    @Override
    public Optional<Admin> findByUser(User user) {
        TypedQuery<Admin> query = entityManager.createQuery("from Admin a where a.user = :user", Admin.class);
        query.setParameter("user", user);
        List<Admin> adminList = query.getResultList();
        if(adminList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(adminList.get(0));
    }

    @Override
    public void update(Admin admin) {
        entityManager.createQuery(
                "update Admin a set a.dob = :dob, a.weight = :weight, a.height = :height" +
                        "a.phoneNo = :phoneNo, a.bloodGroup = :bloodGroup where a.user = :user")
                .setParameter("dob", admin.getDob())
                .setParameter("weight", admin.getWeight())
                .setParameter("height", admin.getHeight())
                .setParameter("phoneNo", admin.getPhoneNo())
                .setParameter("bloodGroup", admin.getBloodGroup())
                .setParameter("user", admin.getUser())
                .executeUpdate();
    }

    @Override
    public Admin getAdminProfile(User user) {
        TypedQuery<Admin> query = entityManager.createQuery("from Admin a where a.user = :user", Admin.class);
        query.setParameter("user", user);
        List<Admin> adminList = query.getResultList();
        if(adminList.isEmpty()) {
            return new Admin();
        }
        return adminList.get(0);
    }
}
