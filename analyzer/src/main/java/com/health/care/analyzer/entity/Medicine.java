package com.health.care.analyzer.entity;

import com.health.care.analyzer.entity.Prescription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "serial_no", nullable = false)
    private String serialNo;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "prescription_medicine",
            joinColumns = @JoinColumn(name = "medicine_id"),
            inverseJoinColumns = @JoinColumn(name = "prescription_id")
    )
    private List<Prescription> prescriptionList;

    public void addPrescription(Prescription prescription) {
        if(prescriptionList == null) {
            prescriptionList = new ArrayList<>();
        }
        prescriptionList.add(prescription);
    }

    public void removePrescription(Prescription prescription) {
        if(prescriptionList != null) {
            prescriptionList.remove(prescription);
        }
    }
}
