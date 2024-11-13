package com.nisanth.respository;

import com.nisanth.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Long> {
    Optional<Otp> findOtpByEmail(String email);
}
