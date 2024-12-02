package com.health.care.analyzer.dto.doctor.designation;

import com.health.care.analyzer.entity.Designation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesignationResponseDTO {
    private String qualification;
    private String collegeName;

    public DesignationResponseDTO(Designation designation) {
        this.qualification = designation.getQualification();
        this.collegeName = designation.getCollegeName();
    }
}
