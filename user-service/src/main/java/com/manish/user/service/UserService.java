package com.manish.user.service;

import com.manish.user.dto.TokenResponseDTO;
import com.manish.user.dto.UserDetailsResponseDTO;
import com.manish.user.dto.UserLoginRequestDTO;
import com.manish.user.dto.UserRegisterRequestDTO;
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
    private final AuthProxy authProxy;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> registerUser(@Valid UserRegisterRequestDTO requestDTO) {
        log.info("|| registerUser got called from UserService class ||");

        Optional<User> userExist = userRepository.findByEmail(requestDTO.getEmail());

        if (userExist.isPresent())
            throw new ApplicationException("User already exist");

        try {
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
                    .eventCreated(new ArrayList<>())
                    .eventJoined(new ArrayList<>())
                    .build();

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

        return new ResponseEntity<>(new TokenResponseDTO(userOptional.get().getUserId(), token), HttpStatus.OK);
    }

    public ResponseEntity<UserDetailsResponseDTO> getUserDetails(String userId) {
        log.info("|| getUserDetails got called from UserService class with user id : {} ||", userId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new ApplicationException("user with user id : " + userId + "not found");

        UserDetailsResponseDTO responseDTO = UserDetailsResponseDTO.builder()
                .firstname(userOptional.get().getFirstname())
                .lastname(userOptional.get().getLastname())
                .email(userOptional.get().getEmail())
                .roles(userOptional.get().getRoles())
                .address(userOptional.get().getAddress())
                .phonenumberList(userOptional.get().getPhonenumberList())
                // TODO: fetch the created events by user id from event service
                .eventCreated(userOptional.get().getEventCreated())
                // TODO: fetch the joined events by user id from event service
                .eventJoined(userOptional.get().getEventJoined())
                .build();

        return new ResponseEntity<UserDetailsResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
