package com.health.care.analyzer.dao.medicine;

import com.health.care.analyzer.entity.medicineEntity.Medicine;

import java.time.LocalDate;
import java.util.Optional;

public interface MedicineDAO {
    Optional<Medicine> findMedicineByNameSerialNoExpiryDate(String name, String serialNo, LocalDate expiryDate);
    Medicine save(Medicine medicine);
    Medicine merge(Medicine medicine);
}
