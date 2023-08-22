package com.manish.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phonenumber {
    @Id
    @NotBlank
    private String phoneId;
    @Pattern(regexp = "^(\\+?\\d{1,3}|\\d{1,4})$", message = "enter a valid country code")
    private String countryCode;
    @Pattern(regexp = "^\\d{10}$", message = "enter a valid phone number")
    private String number;
    @NotEmpty(message = "type is required")
    private String type;
}
