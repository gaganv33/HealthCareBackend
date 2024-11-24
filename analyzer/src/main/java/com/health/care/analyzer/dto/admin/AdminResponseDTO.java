package com.health.care.analyzer.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDTO {
    private Date dob;
    private String phoneNo;
    private String bloodGroup;
    private Integer weight;
    private Integer height;
}
