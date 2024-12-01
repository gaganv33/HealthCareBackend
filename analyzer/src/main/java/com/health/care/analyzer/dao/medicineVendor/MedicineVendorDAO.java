package com.health.care.analyzer.dao.medicineVendor;

import com.health.care.analyzer.entity.medicineEntity.MedicineVendor;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.Optional;

public interface MedicineVendorDAO {
    Optional<MedicineVendor> getMedicineVendorUsingUser(User medicineVendorUser);
    void updateAddressUsingUser(User medicineVendorUser, String address);
}
