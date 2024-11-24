package com.health.care.analyzer.dao.user;

import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> userList = query.getResultList();
        if(userList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userList.get(0));
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        TypedQuery<User> query = entityManager.createQuery("from User u", User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getAllAdmin() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role", User.class);
        query.setParameter("role", "ROLE_ADMIN");
        return query.getResultList();
    }

    @Override
    public List<User> getAllDoctor() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role", User.class);
        query.setParameter("role", "ROLE_DOCTOR");
        return query.getResultList();
    }

    @Override
    public List<User> getAllPatient() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role", User.class);
        query.setParameter("role", "ROLE_PATIENT");
        return query.getResultList();
    }

    @Override
    public List<User> getAllReceptionist() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role", User.class);
        query.setParameter("role", "ROLE_RECEPTIONIST");
        return query.getResultList();
    }

    @Override
    public List<User> getAllPhlebotomist() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role", User.class);
        query.setParameter("role", "ROLE_PHLEBOTOMIST");
        return query.getResultList();
    }

    @Override
    public List<User> getEnabledUser() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.isEnabled = :isEnabled", User.class);
        query.setParameter("isEnabled", true);
        return query.getResultList();
    }

    @Override
    public List<User> getDisabledUser() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.isEnabled = :isEnabled", User.class);
        query.setParameter("isEnabled", false);
        return query.getResultList();
    }

    @Override
    public List<User> getAllEnabledAdmin() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_ADMIN");
        query.setParameter("isEnabled", true);
        return query.getResultList();
    }

    @Override
    public List<User> getAllDisabledAdmin() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_ADMIN");
        query.setParameter("isEnabled", false);
        return query.getResultList();
    }

    @Override
    public List<User> getAllEnabledDoctor() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_DOCTOR");
        query.setParameter("isEnabled", true);
        return query.getResultList();
    }

    @Override
    public List<User> getAllDisabledDoctor() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_DOCTOR");
        query.setParameter("isEnabled", false);
        return query.getResultList();
    }

    @Override
    public List<User> getAllEnabledPatient() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_PATIENT");
        query.setParameter("isEnabled", true);
        return query.getResultList();
    }

    @Override
    public List<User> getAllDisabledPatient() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_PATIENT");
        query.setParameter("isEnabled", false);
        return query.getResultList();
    }

    @Override
    public List<User> getAllEnabledReceptionist() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_RECEPTIONIST");
        query.setParameter("isEnabled", true);
        return query.getResultList();
    }

    @Override
    public List<User> getAllDisabledReceptionist() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_RECEPTIONIST");
        query.setParameter("isEnabled", false);
        return query.getResultList();
    }

    @Override
    public List<User> getAllEnabledPhlebotomist() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_PHLEBOTOMIST");
        query.setParameter("isEnabled", true);
        return query.getResultList();
    }

    @Override
    public List<User> getAllDisabledPhlebotomist() {
        TypedQuery<User> query = entityManager.createQuery("from User u where u.role = :role and u.isEnabled = :isEnabled",
                User.class);
        query.setParameter("role", "ROLE_PHLEBOTOMIST");
        query.setParameter("isEnabled", false);
        return query.getResultList();
    }

    @Override
    public void enableUser(String username) {
        entityManager.createQuery("update User u set u.isEnabled = :isEnabled where u.username = :username")
                .setParameter("isEnabled", true)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public void disableUser(String username) {
        entityManager.createQuery("update User u set u.isEnabled = :isEnabled where u.username = :username")
                .setParameter("isEnabled", false)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public void deleteUser(String username) {
        entityManager.createQuery("delete User u where u.username = :username")
                .setParameter("username", username)
                .executeUpdate();
    }
}
