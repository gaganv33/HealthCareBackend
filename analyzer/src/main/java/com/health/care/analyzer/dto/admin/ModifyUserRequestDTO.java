package com.health.care.analyzer.dto.admin;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyUserRequestDTO {
    @NotBlank(message = "username cannot be blank")
    private String username;
}
