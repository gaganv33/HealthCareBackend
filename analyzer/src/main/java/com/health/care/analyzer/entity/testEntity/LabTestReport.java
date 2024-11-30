package com.health.care.analyzer.entity.testEntity;

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
    @Column(name = "name")
    private String name;

    @Column(name = "result", nullable = false)
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
}
