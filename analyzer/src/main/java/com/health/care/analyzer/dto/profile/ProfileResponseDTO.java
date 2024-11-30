package com.health.care.analyzer.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDTO {
    private LocalDate dob;
    private String phoneNo;
    private String bloodGroup;
    private Integer weight;
    private Integer height;
}
