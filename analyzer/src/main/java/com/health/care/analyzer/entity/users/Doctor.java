package com.health.care.analyzer.entity.users;

import com.health.care.analyzer.dto.doctor.DoctorRequestDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Designation;
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
@Table(name = "doctor")
public class Doctor {
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private List<Designation> designationList;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointmentList;

    public void addDesignation(Designation designation) {
        if(designationList == null) {
            designationList = new ArrayList<>();
        }
        designationList.add(designation);
    }

    public void addAppointment(Appointment appointment) {
        if(appointmentList == null) {
            appointmentList = new ArrayList<>();
        }
        appointmentList.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        if(appointmentList != null) {
            appointmentList.remove(appointment);
        }
    }

    public Doctor(DoctorRequestDTO doctorRequestDTO) {
        this.dob = doctorRequestDTO.getDob();
        this.registeredDate = doctorRequestDTO.getRegisteredDate();
        this.phoneNo = doctorRequestDTO.getPhoneNo();
        this.bloodGroup = doctorRequestDTO.getBloodGroup();
    }
}
