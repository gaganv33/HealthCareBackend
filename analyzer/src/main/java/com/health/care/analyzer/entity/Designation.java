package com.health.care.analyzer.entity;

import com.health.care.analyzer.dto.doctor.designation.DesignationRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "designation")
public class Designation {
    @Id
    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "college_name", nullable = false)
    private String collegeName;

    public Designation(DesignationRequestDTO designationRequestDTO) {
        this.qualification = designationRequestDTO.getQualification();
        this.collegeName = designationRequestDTO.getCollegeName();
    }
}
