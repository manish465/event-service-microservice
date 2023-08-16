package com.manish.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String userId;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    private String roles;
    @OneToOne
    private Address address;
    @OneToMany
    private List<Phonenumber> phonenumberList;
    private List<String> eventCreated;
    private List<String> eventJoined;
}
