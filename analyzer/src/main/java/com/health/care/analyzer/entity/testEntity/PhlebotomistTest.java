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

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "phlebotomist_test_phlebotomist",
            joinColumns = @JoinColumn(name = "phlebotomist_test_id"),
            inverseJoinColumns = @JoinColumn(name = "phlebotomist_id")
    )
    private List<Phlebotomist> phlebotomistList;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "phlebotomist_test_lab_test",
            joinColumns = @JoinColumn(name = "phlebotomist_test_id"),
            inverseJoinColumns = @JoinColumn(name = "lab_test_id")
    )
    private List<LabTest> labTestList;

    public void addPhlebotomist(Phlebotomist phlebotomist) {
        if(phlebotomistList == null) {
            phlebotomistList = new ArrayList<>();
        }
        phlebotomistList.add(phlebotomist);
    }

    public void removePhlebotomist(Phlebotomist phlebotomist) {
        if(phlebotomistList != null) {
            phlebotomistList.remove(phlebotomist);
        }
    }

    public void addLabTest(LabTest labTest) {
        if(labTestList == null) {
            labTestList = new ArrayList<>();
        }
        labTestList.add(labTest);
    }

    public void removeLabTest(LabTest labTest) {
        if(labTestList != null) {
            labTestList.remove(labTest);
        }
    }
}
