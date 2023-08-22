package com.manish.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @NotBlank
    private String addressId;
    @Size(min = 5, max = 40, message = "address field should not be less then 4 characters and more then 40 characters")
    private String address;
    @Size(max = 30, message = "street field should not be more then 30 characters")
    private String street;
    @Size(max = 50, message = "landmark field should not be more then 15 characters")
    private String landmark;
    @Size(min = 2, max = 15, message = "city field should not be less then 2 characters and more then 15 characters")
    private String city;
    @Pattern(regexp = "^\\d{6}$", message = "enter a valid zipcode")
    private String zipcode;
    @Size(min = 2, max = 15, message = "state field should not be less then 2 characters and more then 15 characters")
    private String state;
    @Size(min = 3, max = 20, message = "country should not be less then 3 characters and more then 20 characters")
    private String country;
    private String extraInfo;
}
