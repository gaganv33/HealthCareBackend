package com.health.care.analyzer.entity;

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
@Table(name = "lab_test")
public class LabTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "phlebotomist_test_lab_test",
            joinColumns = @JoinColumn(name = "lab_test_id"),
            inverseJoinColumns = @JoinColumn(name = "phlebotomist_test_id")
    )
    private List<PhlebotomistTest> phlebotomistTestList;

    public void addPhlebotomistTest(PhlebotomistTest phlebotomistTest) {
        if(phlebotomistTestList == null) {
            phlebotomistTestList = new ArrayList<>();
        }
        phlebotomistTestList.add(phlebotomistTest);
    }

    public void removePhlebotomistTest(PhlebotomistTest phlebotomistTest) {
        if(phlebotomistTestList != null) {
            phlebotomistTestList.remove(phlebotomistTest);
        }
    }
}
