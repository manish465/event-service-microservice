package com.manish.user.service;

import com.manish.user.dto.UserRegisterRequestDTO;
import com.manish.user.entity.Address;
import com.manish.user.entity.Phonenumber;
import com.manish.user.entity.User;
import com.manish.user.exception.ApplicationException;
import com.manish.user.exception.UserAlreadyExist;
import com.manish.user.repository.AddressRepository;
import com.manish.user.repository.PhoneRepository;
import com.manish.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    public ResponseEntity<String> registerUser(@Valid UserRegisterRequestDTO requestDTO){
        log.info("|| registerUser got called from UserService class ||");

        Optional<User> userExist = userRepository.findByEmail(requestDTO.getEmail());

        if(userExist.isPresent()){
            throw new UserAlreadyExist("User already exist");
        }

        try{
            Address address = Address.builder()
                    .addressId(UUID.randomUUID().toString())
                    .address(requestDTO.getAddress().getAddress())
                    .street(requestDTO.getAddress().getStreet())
                    .landmark(requestDTO.getAddress().getLandmark())
                    .city(requestDTO.getAddress().getCity())
                    .zipcode(requestDTO.getAddress().getZipcode())
                    .state(requestDTO.getAddress().getState())
                    .country(requestDTO.getAddress().getCountry())
                    .extraInfo(requestDTO.getAddress().getExtraInfo())
                    .build();

            addressRepository.save(address);

            List<Phonenumber> phonenumberList = new ArrayList<>();

            requestDTO.getPhonenumber().forEach(phone -> phonenumberList.add(Phonenumber.builder()
                    .phoneId(UUID.randomUUID().toString())
                    .countryCode(phone.getCountryCode())
                    .number(phone.getNumber())
                    .type(phone.getType())
                    .build()));

            phonenumberList.forEach(phoneRepository::save);

            User user = User.builder()
                    .userId(UUID.randomUUID().toString())
                    .firstname(requestDTO.getFirstname())
                    .lastname(requestDTO.getLastname())
                    .email(requestDTO.getEmail())
                    .password(passwordEncoder.encode(requestDTO.getPassword()))
                    .roles(requestDTO.getRoles())
                    .address(address)
                    .phonenumberList(phonenumberList)
                    .build();

            userRepository.save(user);

            return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }
}
