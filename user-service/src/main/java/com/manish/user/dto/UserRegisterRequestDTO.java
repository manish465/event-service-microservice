package com.manish.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @Size(min = 3, max = 20, message = "firstname field should not be less then 3 characters and more then 20 characters")
    private String firstname;
    @Size(min = 3, max = 20, message = "lastname field should not be less then 3 characters and more then 20 characters")
    private String lastname;
    @Email(message = "enter a valid email")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "password field should have minimum 8 characters, at least one letter and one number")
    private String password;
    @NotBlank(message = "role is required")
    private String roles;
    @Valid
    private AddressRegisterRequestDTO address;
    @Valid
    private List<PhonenumberRegisterRequestDTO> phonenumber;
}
