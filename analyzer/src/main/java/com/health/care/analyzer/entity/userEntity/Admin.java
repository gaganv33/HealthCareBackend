package com.health.care.analyzer.entity.userEntity;

import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "admin")
public class Admin {
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

    // need to change it to nullable = false
    @Column(name = "dashboard_password", nullable = true)
    private String dashboardPassword;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Admin(ProfileRequestDTO profileRequestDTO) {
        this.dob = profileRequestDTO.getDob();
        this.registeredDate = profileRequestDTO.getRegisteredDate();
        this.phoneNo = profileRequestDTO.getPhoneNo();
        this.bloodGroup = profileRequestDTO.getBloodGroup();
    }
}
