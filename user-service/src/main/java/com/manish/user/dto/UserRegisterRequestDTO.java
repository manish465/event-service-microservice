package com.manish.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequestDTO {
    @NotEmpty(message = "firstname is required")
    @Max(value = 20, message = "firstname should not be more then 20 characters")
    @Min(value = 3, message = "firstname should not be less then 3 characters")
    private String firstname;
    @NotEmpty(message = "lastname is required")
    @Max(value = 20, message = "lastname should not be more then 20 characters")
    @Min(value = 3, message = "lastname should not be less then 3 characters")
    private String lastname;
    @NotEmpty(message = "email is required")
    @Email(message = "enter a valid email")
    private String email;
    @NotEmpty(message = "password is required")
    @Max(value = 30, message = "password should not be more then 30 characters")
    @Min(value = 8, message = "password should not be less then 8 characters")
    private String password;
    @NotEmpty(message = "role is required")
    private String roles;
    @Valid
    private AddressRegisterRequestDTO address;
    @Valid
    private List<PhonenumberRegisterRequestDTO> phonenumber;
}
