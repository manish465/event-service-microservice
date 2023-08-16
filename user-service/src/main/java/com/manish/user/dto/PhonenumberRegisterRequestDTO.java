package com.manish.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhonenumberRegisterRequestDTO {
    @NotEmpty(message = "country code is required")
    @Pattern(regexp = "^(\\+?\\d{1,3}|\\d{1,4})$", message = "enter a valid country code")
    private Integer countryCode;
    @NotEmpty(message = "phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "enter a valid phone number")
    private Long number;
    @NotEmpty(message = "type is required")
    private String type;
}
