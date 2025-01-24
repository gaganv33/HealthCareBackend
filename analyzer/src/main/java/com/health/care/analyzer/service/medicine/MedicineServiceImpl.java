package com.health.care.analyzer.service.medicine;

import com.health.care.analyzer.dao.medicine.MedicineDAO;
import com.health.care.analyzer.entity.medicineEntity.Medicine;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {
    private final MedicineDAO medicineDAO;

    @Autowired
    public MedicineServiceImpl(MedicineDAO medicineDAO) {
        this.medicineDAO = medicineDAO;
    }

    @Override
    public Optional<Medicine> findMedicineByNameSerialNoExpiryDate(String name, String serialNo, LocalDate expiryDate) {
        return medicineDAO.findMedicineByNameSerialNoExpiryDate(name, serialNo, expiryDate);
    }

    @Override
    @Transactional
    public Medicine save(Medicine medicine) {
        return medicineDAO.save(medicine);
    }

    @Override
    @Transactional
    public Medicine merge(Medicine medicine) {
        return medicineDAO.merge(medicine);
    }

    @Override
    public List<Medicine> findBySerialNo(String serialNo) {
        return medicineDAO.findBySerialNo(serialNo);
    }

    @Override
    public Optional<Medicine> getMedicineUsingMedicineNameAndQuantity(String name, Integer quantity) {
        return medicineDAO.getMedicineUsingMedicineNameAndQuantity(name, quantity);
    }

    @Override
    public Optional<Medicine> findMedicineBySerialNo(String serialNo) {
        return medicineDAO.findMedicineBySerialNo(serialNo);
    }

    @Override
    @Transactional
    public void deleteBySerialNo(String serialNo) {
        medicineDAO.deleteBySerialNo(serialNo);
    }
}
