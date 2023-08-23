package com.minble.client.repository;

import com.minble.client.domain.Member;
import com.minble.client.domain.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
    Page<Payment> findByMember(Member member, Pageable pageable);
    
}
