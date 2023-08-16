package com.manish.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class AddressRegisterRequestDTO {
    @NotEmpty(message = "address is required")
    @Max(value = 40, message = "address should not be more then 40 characters")
    @Min(value = 5, message = "address should not be less then 4 characters")
    private String address;
    @Max(value = 30, message = "street should not be more then 30 characters")
    private String street;
    @Max(value = 15, message = "landmark should not be more then 15 characters")
    private String landmark;
    @NotEmpty(message = "city is required")
    @Max(value = 15, message = "city should not be more then 15 characters")
    @Min(value = 2, message = "city should not be less then 4 characters")
    private String city;
    @NotEmpty(message = "zipcode is required")
    @Pattern(regexp = "^\\d{6}$", message = "zipcode should not be more then 15 characters")
    private Integer zipcode;
    @NotEmpty(message = "state is required")
    @Max(value = 15, message = "state should not be more then 15 characters")
    @Min(value = 2, message = "state should not be less then 4 characters")
    private String state;
    @NotEmpty(message = "country is required")
    @Max(value = 20, message = "country should not be more then 15 characters")
    @Min(value = 3, message = "country should not be less then 4 characters")
    private String country;
    private String extraInfo;
}
