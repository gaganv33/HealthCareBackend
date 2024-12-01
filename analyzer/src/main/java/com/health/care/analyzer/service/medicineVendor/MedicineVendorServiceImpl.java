package com.health.care.analyzer.service.medicineVendor;

import com.health.care.analyzer.dao.medicineVendor.MedicineVendorDAO;
import com.health.care.analyzer.entity.medicineEntity.MedicineVendor;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicineVendorServiceImpl implements MedicineVendorService {
    private final MedicineVendorDAO medicineVendorDAO;

    @Autowired
    public MedicineVendorServiceImpl(MedicineVendorDAO medicineVendorDAO) {
        this.medicineVendorDAO = medicineVendorDAO;
    }

    @Override
    public Optional<MedicineVendor> getMedicineVendorUsingUser(User medicineVendorUser) {
        return medicineVendorDAO.getMedicineVendorUsingUser(medicineVendorUser);
    }

    @Override
    @Transactional
    public void updateAddressUsingUser(User medicineVendorUser, String address) {
        medicineVendorDAO.updateAddressUsingUser(medicineVendorUser, address);
    }
}
