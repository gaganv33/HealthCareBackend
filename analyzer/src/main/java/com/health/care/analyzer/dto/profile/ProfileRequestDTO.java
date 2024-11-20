package com.health.care.analyzer.dto.profile;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequestDTO {

    @NotNull(message = "Date of birth should not be null")
    @Past(message = "Date of birth must be in the past")
    private Date dob;

    @NotNull(message = "Registered date should not be null")
    @PastOrPresent(message = "Registered date must be in the past or present")
    private Date registeredDate;

    @NotBlank(message = "Phone number should not be blank")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phoneNo;

    @NotBlank(message = "Blood group should not be blank")
    @Pattern(
            regexp = "^(A|B|AB|O)[+-]$",
            message = "Blood group must be A+, A-, B+, B-, AB+, AB-, O+, or O-"
    )
    private String bloodGroup;
}
