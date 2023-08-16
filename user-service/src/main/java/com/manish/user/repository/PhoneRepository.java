package com.manish.user.repository;

import com.manish.user.entity.Phonenumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phonenumber, String> {
}
