package com.manish.user.dto;

import java.util.List;

import com.manish.user.entity.Address;
import com.manish.user.entity.Phonenumber;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {
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
    private Address address;
    @Valid
    private List<Phonenumber> phonenumberList;
}
