package com.health.care.analyzer.service.prescription;

import com.health.care.analyzer.dao.prescription.PrescriptionDAO;
import com.health.care.analyzer.data.Stage;
import com.health.care.analyzer.dto.prescription.NotAssignedPrescription;
import com.health.care.analyzer.dto.receptionist.OnSavePrescriptionMedicineListDTO;
import com.health.care.analyzer.entity.Prescription;
import com.health.care.analyzer.entity.medicineEntity.AvailableMedicineRecord;
import com.health.care.analyzer.entity.medicineEntity.Medicine;
import com.health.care.analyzer.entity.medicineEntity.MedicineRecord;
import com.health.care.analyzer.entity.userEntity.Receptionist;
import com.health.care.analyzer.service.medicine.MedicineService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionDAO prescriptionDAO;
    private final MedicineService medicineService;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionDAO prescriptionDAO, MedicineService medicineService) {
        this.prescriptionDAO = prescriptionDAO;
        this.medicineService = medicineService;
    }

    @Override
    public List<NotAssignedPrescription> getNotAssignedPrescription() {
        List<Prescription> prescriptionList = prescriptionDAO.getNotAssignedPrescription();
        return prescriptionList.stream().map(NotAssignedPrescription::new).collect(Collectors.toList());
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionDAO.getPrescriptionById(id);
    }

    @Override
    @Transactional
    public boolean updatePrescriptionUsingIdAndReceptionist(Long id, Receptionist receptionist) {
        Optional<Prescription> optionalPrescription = getPrescriptionById(id);
        if(optionalPrescription.isEmpty()) {
            return false;
        }
        Prescription prescription = optionalPrescription.get();
        if(!prescription.getAppointment().getStage().equals(Stage.RECEPTIONIST) ||
                (prescription.getReceptionist() != null &&
                !prescription.getReceptionist().getUser().getUsername().equals(receptionist.getUser().getUsername()))) {
            return false;
        }
        prescription.setReceptionist(receptionist);
        prescriptionDAO.merge(prescription);
        return true;
    }

    @Override
    public Optional<Prescription> getPrescriptionUsingIdAndReceptionist(Long id, Receptionist receptionist) {
        return prescriptionDAO.getPrescriptionUsingIdAndReceptionist(id, receptionist);
    }

    @Override
    @Transactional
    public OnSavePrescriptionMedicineListDTO onSavePrescriptionMedicineList(Prescription prescription) {
        List<MedicineRecord> requiredMedicineList = prescription.getRequiredMedicineList();
        List<AvailableMedicineRecord> availableMedicineList = new ArrayList<>();
        List<MedicineRecord> pendingMedicineList = new ArrayList<>();

        OnSavePrescriptionMedicineListDTO onSavePrescriptionMedicineListDTO = new OnSavePrescriptionMedicineListDTO(requiredMedicineList);

        for(MedicineRecord medicineRecord : requiredMedicineList) {
            Optional<Medicine> medicine = medicineService.getMedicineUsingMedicineNameAndQuantity(medicineRecord.getName(), medicineRecord.getQuantity());
            if(medicine.isEmpty()) {
                onSavePrescriptionMedicineListDTO.addMedicineRecordToPendingMedicineList(medicineRecord);
                pendingMedicineList.add(medicineRecord);
            } else {
                onSavePrescriptionMedicineListDTO.addMedicineRecordToAvailableMedicineList(medicine.get());
                Medicine medicineAvailable = medicine.get();
                availableMedicineList.add(AvailableMedicineRecord.builder()
                                .name(medicineAvailable.getName())
                                .quantity(medicineAvailable.getQuantity())
                                .serialNo(medicineAvailable.getSerialNo())
                                .expiryDate(medicineAvailable.getExpiryDate())
                                .build());
            }
        }
        if(!pendingMedicineList.isEmpty()) {
            onSavePrescriptionMedicineListDTO.setMessage("Some medicine are pending.");
        } else {
            onSavePrescriptionMedicineListDTO.setMessage("Prescription completed. All medicine are available.");
            prescription.getAppointment().setStage(Stage.COMPLETED);
        }
        prescription.setPendingMedicineList(pendingMedicineList);
        prescription.setAvailableMedicineList(availableMedicineList);
        prescriptionDAO.merge(prescription);
        return onSavePrescriptionMedicineListDTO;
    }

    @Override
    public List<Prescription> getPrescriptionUsingReceptionist(Receptionist receptionist) {
        return prescriptionDAO.getPrescriptionUsingReceptionist(receptionist);
    }
}
