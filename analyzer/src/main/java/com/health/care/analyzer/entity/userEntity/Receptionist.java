package com.health.care.analyzer.entity.userEntity;

import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.entity.Prescription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "registered_date", nullable = false)
    private Date registeredDate;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "blood_group", nullable = false)
    private String bloodGroup;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "receptionist", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    }, fetch = FetchType.LAZY)
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
        this.registeredDate = profileRequestDTO.getRegisteredDate();
        this.phoneNo = profileRequestDTO.getPhoneNo();
        this.bloodGroup = profileRequestDTO.getBloodGroup();
    }
}
