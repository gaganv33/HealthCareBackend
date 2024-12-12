package com.health.care.analyzer.entity.testEntity;

import com.health.care.analyzer.dto.labTestReport.LabTestReportDTO;
import com.health.care.analyzer.dto.labTestReport.LabTestRequestDTO;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lab_test")
public class LabTestReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "result", nullable = true)
    private String result;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "phlebotomist_id")
    private Phlebotomist phlebotomist;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "phlebotomist_test_id")
    private PhlebotomistTest phlebotomistTest;

    public LabTestReport(LabTestRequestDTO labTestRequestDTO) {
        this.name = labTestRequestDTO.getName();
        this.details = labTestRequestDTO.getDetails();
    }
}
