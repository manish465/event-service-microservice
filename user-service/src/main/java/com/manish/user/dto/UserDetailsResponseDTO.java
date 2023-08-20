package com.manish.user.dto;

import java.util.List;

import com.manish.user.entity.Address;
import com.manish.user.entity.Phonenumber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponseDTO {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String roles;
    private Address address;
    private List<Phonenumber> phonenumberList;
    private List<String> eventCreated;
    private List<String> eventJoined;
}
