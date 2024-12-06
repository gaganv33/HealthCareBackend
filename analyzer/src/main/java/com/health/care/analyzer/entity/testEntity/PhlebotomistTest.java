package com.health.care.analyzer.entity.testEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phlebotomist_test")
public class PhlebotomistTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "phlebotomistTest", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    private Appointment appointment;

    @OneToMany(mappedBy = "phlebotomistTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
}
