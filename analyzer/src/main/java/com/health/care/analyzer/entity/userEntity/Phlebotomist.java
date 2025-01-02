package com.health.care.analyzer.entity.userEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "phlebotomist")
public class Phlebotomist {
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

    @OneToMany(mappedBy = "phlebotomist", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    private List<LabTestReport> labTestReportList;

    public void addLabTestReport(LabTestReport labTestReport) {
        if(labTestReportList == null) {
            labTestReportList = new ArrayList<>();
        }
        labTestReportList.add(labTestReport);
    }

    public void removeLabTestReport(LabTestReport labTestReport) {
        if(labTestReportList != null) {
            labTestReportList.remove(labTestReport);
        }
    }

    public Phlebotomist(ProfileRequestDTO profileRequestDTO) {
        this.dob = profileRequestDTO.getDob();
        this.phoneNo = profileRequestDTO.getPhoneNo();
        this.bloodGroup = profileRequestDTO.getBloodGroup();
        this.weight = profileRequestDTO.getWeight();
        this.height = profileRequestDTO.getHeight();
    }
}
