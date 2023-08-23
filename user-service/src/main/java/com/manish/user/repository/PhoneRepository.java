package com.manish.user.repository;

import com.manish.user.entity.Phonenumber;
import com.manish.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phonenumber, String> {
    List<Phonenumber> findAllByUser(User user);
}
