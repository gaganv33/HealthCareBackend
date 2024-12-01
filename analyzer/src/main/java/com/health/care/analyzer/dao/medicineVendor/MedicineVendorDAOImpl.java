package com.health.care.analyzer.dao.medicineVendor;

import com.health.care.analyzer.entity.medicineEntity.MedicineVendor;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicineVendorDAOImpl implements MedicineVendorDAO {
    private final EntityManager entityManager;

    @Autowired
    public MedicineVendorDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<MedicineVendor> getMedicineVendorUsingUser(User medicineVendorUser) {
        TypedQuery<MedicineVendor> query = entityManager.createQuery(
                "from MedicineVendor m where m.user = :user", MedicineVendor.class);
        query.setParameter("user", medicineVendorUser);
        List<MedicineVendor> medicineVendorList = query.getResultList();
        if(medicineVendorList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(medicineVendorList.get(0));
    }

    @Override
    public void updateAddressUsingUser(User medicineVendorUser, String address) {
        entityManager.createQuery("update MedicineVendor m set m.address = :address where m.user = :user")
                .setParameter("address", address)
                .setParameter("user", medicineVendorUser).executeUpdate();
    }
}
