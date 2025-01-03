package com.health.care.analyzer.entity.userEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.health.care.analyzer.dto.auth.MedicineVendorRegisterRequestDTO;
import com.health.care.analyzer.dto.user.UserRequestDTO;
import com.health.care.analyzer.entity.medicineEntity.MedicineVendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User {
    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "registered_date", nullable = false)
    private LocalDate registeredDate;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Admin admin;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Patient patient;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Phlebotomist phlebotomist;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Receptionist receptionist;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Doctor doctor;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private MedicineVendor medicineVendor;

    public User(UserRequestDTO userRequestDTO) {
        this.username = userRequestDTO.getUsername();
        this.firstName = userRequestDTO.getFirstName();
        this.lastName = userRequestDTO.getLastName();
        this.password = userRequestDTO.getPassword();
        this.role = userRequestDTO.getRole();
    }

    public User(MedicineVendorRegisterRequestDTO medicineVendorRegisterRequestDTO) {
        this.username = medicineVendorRegisterRequestDTO.getUsername();
        this.firstName = medicineVendorRegisterRequestDTO.getFirstName();
        this.lastName = medicineVendorRegisterRequestDTO.getLastName();
        this.password = medicineVendorRegisterRequestDTO.getPassword();
        this.role = medicineVendorRegisterRequestDTO.getRole();
    }
}
