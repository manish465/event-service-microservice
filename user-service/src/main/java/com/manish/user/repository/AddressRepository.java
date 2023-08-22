package com.manish.user.repository;

import com.manish.user.entity.Address;
import com.manish.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByUser(User user);
}
