package com.health.care.analyzer.dao.medicine;

import com.health.care.analyzer.entity.medicineEntity.Medicine;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicineDAO {
    Optional<Medicine> findMedicineByNameSerialNoExpiryDate(String name, String serialNo, LocalDate expiryDate);
    Medicine save(Medicine medicine);
    Medicine merge(Medicine medicine);
    List<Medicine> findBySerialNo(String serialNo);
    Optional<Medicine> getMedicineUsingMedicineNameAndQuantity(String name, Integer quantity);
    Optional<Medicine> findMedicineBySerialNo(String serialNo);
    void deleteBySerialNo(String serialNo);
}
