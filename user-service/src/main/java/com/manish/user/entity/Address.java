package com.manish.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    private String addressId;
    private String address;
    private String street;
    private String landmark;
    private String city;
    private String zipcode;
    private String state;
    private String country;
    private String extraInfo;
}
