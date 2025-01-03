package com.health.care.analyzer.dao.medicine;

import com.health.care.analyzer.entity.medicineEntity.Medicine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicineDAOImpl implements MedicineDAO {
    private final EntityManager entityManager;

    @Autowired
    public MedicineDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Medicine> findMedicineByNameSerialNoExpiryDate(String name, String serialNo, LocalDate expiryDate) {
        TypedQuery<Medicine> query = entityManager.createQuery(
                "from Medicine m where m.name = :name and m.serialNo = :serialNo and m.expiryDate = :expiryDate",
                Medicine.class
        );
        query.setParameter("name", name);
        query.setParameter("serialNo", serialNo);
        query.setParameter("expiryDate", expiryDate);
        List<Medicine> medicineList = query.getResultList();
        if(medicineList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(medicineList.get(0));
    }

    @Override
    public Medicine save(Medicine medicine) {
        entityManager.persist(medicine);
        return medicine;
    }

    @Override
    public Medicine merge(Medicine medicine) {
        medicine = entityManager.merge(medicine);
        return medicine;
    }

    @Override
    public List<Medicine> findBySerialNo(String serialNo) {
        TypedQuery<Medicine> query = entityManager.createQuery("from Medicine m where m.serialNo = :serialNo",
                Medicine.class);
        query.setParameter("serialNo", serialNo);
        return query.getResultList();
    }
}
