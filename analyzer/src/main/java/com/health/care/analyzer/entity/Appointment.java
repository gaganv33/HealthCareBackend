package com.health.care.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


/*
@UniqueConstraint annotation:
A unique constraint is the rule that the values of a key are valid only if they are unique.
A key that is constrained to have unique values is called a unique key .
A unique constraint is enforced by using a unique index.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stage", nullable = false)
    private String stage;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @JsonBackReference
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonBackReference
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @JsonManagedReference
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Prescription prescription;

    @JsonManagedReference
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private PhlebotomistTest phlebotomistTest;
}
