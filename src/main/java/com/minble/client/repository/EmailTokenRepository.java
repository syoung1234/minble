package com.minble.client.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.minble.client.domain.EmailToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId, LocalDateTime now, boolean expired);
    
}
