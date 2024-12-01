package com.health.care.analyzer.controller.medicineVendor;

import com.health.care.analyzer.dto.medicineVendor.AddMedicineRequestDTO;
import com.health.care.analyzer.dto.medicineVendor.MedicineVendorAddressRequestDTO;
import com.health.care.analyzer.entity.medicineEntity.Medicine;
import com.health.care.analyzer.entity.medicineEntity.MedicineVendor;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.MedicineVendorNotFoundException;
import com.health.care.analyzer.service.medicine.MedicineService;
import com.health.care.analyzer.service.medicineVendor.MedicineVendorService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/medicine/vendor")
@PreAuthorize("hasAuthority('ROLE_MEDICINE_VENDOR')")
public class MedicineVendorController {
    private final UserService userService;
    private final MedicineVendorService medicineVendorService;
    private final MedicineService medicineService;

    @Autowired
    public MedicineVendorController(UserService userService, MedicineVendorService medicineVendorService,
                                    MedicineService medicineService) {
        this.userService = userService;
        this.medicineVendorService = medicineVendorService;
        this.medicineService = medicineService;
    }

    @GetMapping("/address")
    public ResponseEntity<String> getMedicineVendorAddress(HttpServletRequest httpServletRequest)
            throws MedicineVendorNotFoundException {
        User medicineVendorUser = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));
        Optional<MedicineVendor> medicineVendorOptional = medicineVendorService.getMedicineVendorUsingUser(medicineVendorUser);
        if(medicineVendorOptional.isEmpty()) {
            throw new MedicineVendorNotFoundException("Medicine vendor not found");
        }
        return new ResponseEntity<>(medicineVendorOptional.get().getAddress(), HttpStatus.OK);
    }

    @PutMapping("/address")
    public ResponseEntity<String> updateAddress(@RequestBody @Valid MedicineVendorAddressRequestDTO medicineVendorAddressRequestDTO,
                                                HttpServletRequest httpServletRequest) {
        User medicineVendorUser = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));
        medicineVendorService.updateAddressUsingUser(medicineVendorUser, medicineVendorAddressRequestDTO.getAddress());
        return new ResponseEntity<>("Address updated", HttpStatus.OK);
    }

    @PostMapping("/add/medicine")
    public ResponseEntity<String> addMedicine(@RequestBody @Valid AddMedicineRequestDTO addMedicineRequestDTO,
                                              HttpServletRequest httpServletRequest) {
        MedicineVendor medicineVendor = userService.
                getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getMedicineVendor();
        Optional<Medicine> medicineOptional = medicineService.findMedicineByNameSerialNoExpiryDate(
                addMedicineRequestDTO.getName(), addMedicineRequestDTO.getSerialNo(), addMedicineRequestDTO.getExpiryDate());
        if(medicineOptional.isEmpty()) {
            Medicine medicine = new Medicine(addMedicineRequestDTO);
            medicine.addMedicineVendor(medicineVendor);
            medicine = medicineService.save(medicine);
        } else {
            Medicine medicine = medicineOptional.get();
            medicine.setQuantity(medicine.getQuantity() + addMedicineRequestDTO.getQuantity());
            if(!medicine.isContainsMedicineVendor(medicineVendor)) {
                medicine.addMedicineVendor(medicineVendor);
            }
            medicine = medicineService.merge(medicine);
        }
        return new ResponseEntity<>("Medicine added", HttpStatus.OK);
    }
}
