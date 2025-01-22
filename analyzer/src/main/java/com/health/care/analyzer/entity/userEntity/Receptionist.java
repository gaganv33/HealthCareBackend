package com.health.care.analyzer.entity.userEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.entity.Prescription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "receptionist")
public class Receptionist {
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "blood_group", nullable = false)
    private String bloodGroup;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Id
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "receptionist", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    }, fetch = FetchType.EAGER)
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

    public Receptionist(ProfileRequestDTO profileRequestDTO) {
        this.dob = profileRequestDTO.getDob();
        this.phoneNo = profileRequestDTO.getPhoneNo();
        this.bloodGroup = profileRequestDTO.getBloodGroup();
        this.weight = profileRequestDTO.getWeight();
        this.height = profileRequestDTO.getHeight();
    }
}
