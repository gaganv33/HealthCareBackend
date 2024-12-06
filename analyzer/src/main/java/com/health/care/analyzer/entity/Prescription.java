package com.health.care.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.health.care.analyzer.entity.medicineEntity.AvailableMedicineRecord;
import com.health.care.analyzer.entity.medicineEntity.MedicineRecord;
import com.health.care.analyzer.entity.userEntity.Receptionist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @JsonBackReference
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "receptionist_id")
    private Receptionist receptionist;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "required_medicine")
    private List<MedicineRecord> requiredMedicineList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "available_medicine")
    private List<AvailableMedicineRecord> availableMedicineList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pending_medicine")
    private List<MedicineRecord> pendingMedicineList;

    public void addMedicineToRequiredMedicineList(MedicineRecord medicineRecord) {
        if(requiredMedicineList == null) {
            requiredMedicineList = new ArrayList<>();
        }
        requiredMedicineList.add(medicineRecord);
    }

    public void removeMedicineFromRequiredMedicineList(MedicineRecord medicineRecord) {
        if(requiredMedicineList != null) {
            requiredMedicineList.remove(medicineRecord);
        }
    }

    public void addMedicineToAvailableMedicineList(AvailableMedicineRecord medicineRecord) {
        if(availableMedicineList == null) {
            availableMedicineList = new ArrayList<>();
        }
        availableMedicineList.add(medicineRecord);
    }

    public void removeMedicineFromAvailableMedicineList(AvailableMedicineRecord medicineRecord) {
        if(availableMedicineList != null) {
            availableMedicineList.remove(medicineRecord);
        }
    }

    public void addMedicineToPendingMedicineList(MedicineRecord medicineRecord) {
        if(pendingMedicineList == null) {
            pendingMedicineList = new ArrayList<>();
        }
        pendingMedicineList.add(medicineRecord);
    }

    public void removeMedicineFromPendingMedicineList(MedicineRecord medicineRecord) {
        if(pendingMedicineList != null) {
            pendingMedicineList.remove(medicineRecord);
        }
    }
}
