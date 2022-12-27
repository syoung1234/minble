package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
