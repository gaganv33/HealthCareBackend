package com.health.care.analyzer.entity.userEntity;

import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "phlebotomist_test_phlebotomist",
            joinColumns = @JoinColumn(name = "phlebotomist_id"),
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
