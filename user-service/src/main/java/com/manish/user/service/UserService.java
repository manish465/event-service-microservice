package com.manish.user.service;

import com.manish.user.dto.TokenResponseDTO;
import com.manish.user.dto.UserLoginRequestDTO;
import com.manish.user.dto.UserRegisterRequestDTO;
import com.manish.user.dto.UserUpdateRequestDTO;
import com.manish.user.entity.Address;
import com.manish.user.entity.Phonenumber;
import com.manish.user.entity.User;
import com.manish.user.exception.ApplicationException;
import com.manish.user.proxy.AuthProxy;
import com.manish.user.repository.AddressRepository;
import com.manish.user.repository.PhoneRepository;
import com.manish.user.repository.UserRepository;
import com.manish.user.utils.Convertor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;
    private final AuthProxy authProxy;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> registerUser(@Valid UserRegisterRequestDTO requestDTO) {
        log.info("|| registerUser got called from UserService class ||");

        Optional<User> userExist = userRepository.findByEmail(requestDTO.getEmail());

        if (userExist.isPresent())
            throw new ApplicationException("User already exist");

        try {
            User user = User.builder()
                    .firstname(requestDTO.getFirstname())
                    .lastname(requestDTO.getLastname())
                    .email(requestDTO.getEmail())
                    .password(passwordEncoder.encode(requestDTO.getPassword()))
                    .roles(requestDTO.getRoles())
                    .eventCreated(new ArrayList<>())
                    .eventJoined(new ArrayList<>())
                    .build();

            Address address = Address.builder()
                    .address(requestDTO.getAddress().getAddress())
                    .street(requestDTO.getAddress().getStreet())
                    .landmark(requestDTO.getAddress().getLandmark())
                    .city(requestDTO.getAddress().getCity())
                    .zipcode(requestDTO.getAddress().getZipcode())
                    .state(requestDTO.getAddress().getState())
                    .country(requestDTO.getAddress().getCountry())
                    .extraInfo(requestDTO.getAddress().getExtraInfo())
                    .user(user)
                    .build();

            user.setAddress(address);

            List<Phonenumber> phonenumberList = new ArrayList<>();

            requestDTO.getPhonenumber().forEach(phone -> phonenumberList.add(Phonenumber.builder()
                    .countryCode(phone.getCountryCode())
                    .number(phone.getNumber())
                    .type(phone.getType())
                    .user(user)
                    .build()));

            user.setPhonenumberList(phonenumberList);

            userRepository.save(user);

            return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<TokenResponseDTO> loginUser(@Valid UserLoginRequestDTO requestDTO) {
        log.info("|| loginUser got called from UserService class with credentials : {} ||", requestDTO);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));

        log.info("|| done authentication using UsernamePasswordAuthenticationToken with AuthenticationManager ||");

        if (!authentication.isAuthenticated())
            throw new ApplicationException("Invalid credentials");

        log.info("|| user authenticated ||");

        String token = authProxy
                .generateToken(authentication.getName(),
                        Convertor.extractAuthoritiesToString(authentication.getAuthorities()));

        Optional<User> userOptional = userRepository.findByEmail(authentication.getName());
        if(userOptional.isEmpty()) throw new ApplicationException("user not found");

        return new ResponseEntity<>(new TokenResponseDTO(userOptional.get().getUserId(), token), HttpStatus.OK);
    }

    public ResponseEntity<User> getUserByUserId(String userId) {
        log.info("|| getUserDetails got called from UserService class with user id : {} ||", userId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new ApplicationException("user with user id : " + userId + "not found");

        Optional<Address> addressOptional = addressRepository.findByUser(userOptional.get());
        List<Phonenumber> phonenumberList = phoneRepository.findAllByUser(userOptional.get());
        if(addressOptional.isEmpty()) throw new ApplicationException("Data mapping error");
        if(phonenumberList.isEmpty()) throw new ApplicationException("Data mapping error");
        addressOptional.get().setUser(null);
        phonenumberList.forEach(phone -> phone.setUser(null));
        userOptional.get().setPassword("");
        userOptional.get().setAddress(addressOptional.get());
        userOptional.get().setPhonenumberList(phonenumberList);

        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    public ResponseEntity<String> updateUserByUserId(String userId, @Valid UserUpdateRequestDTO requestDTO) {
        log.info("|| updateUserByUserId got called from UserService class with user id : {} ||", userId);

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new ApplicationException("user with user id : " + userId + " not found");

        User user = User.builder()
                .userId(userId)
                .firstname(requestDTO.getFirstname())
                .lastname(requestDTO.getLastname())
                .email(requestDTO.getEmail())
                .password(userOptional.get().getPassword())
                .roles(requestDTO.getRoles())
                .eventCreated(userOptional.get().getEventCreated())
                .eventJoined(userOptional.get().getEventJoined())
                .build();

        Address address = Address.builder()
                        .addressId(requestDTO.getAddress().getAddressId())
                        .address(requestDTO.getAddress().getAddress())
                        .street(requestDTO.getAddress().getStreet())
                        .landmark(requestDTO.getAddress().getLandmark())
                        .city(requestDTO.getAddress().getCity())
                        .zipcode(requestDTO.getAddress().getZipcode())
                        .state(requestDTO.getAddress().getState())
                        .country(requestDTO.getAddress().getCountry())
                        .extraInfo(requestDTO.getAddress().getExtraInfo())
                        .user(user)
                        .build();

        user.setAddress(address);

        List<Phonenumber> phonenumberList = new ArrayList<>();
        requestDTO.getPhonenumberList().forEach(phone -> phonenumberList.add(Phonenumber.builder()
                        .phoneId(phone.getPhoneId())
                        .countryCode(phone.getCountryCode())
                        .number(phone.getNumber())
                        .type(phone.getType())
                        .user(user)
                        .build()));

        user.setPhonenumberList(phonenumberList);

        userRepository.save(user);

        return new ResponseEntity<>("user updated", HttpStatus.OK);
    }

     public ResponseEntity<String> deleteUserByUserId(String userId) {
         log.info("|| deleteUserByUserId got called from UserService class with user id : {} ||", userId);

         Optional<User> userOptional = userRepository.findById(userId);

         if (userOptional.isEmpty())
             throw new ApplicationException("user with user id : " + userId + " not found");

         userRepository.deleteById(userId);

         return new ResponseEntity<>("User deleted", HttpStatus.OK);
     }
}
