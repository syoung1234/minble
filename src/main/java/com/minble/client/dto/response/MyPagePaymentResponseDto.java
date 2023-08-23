package com.minble.client.dto.response;

import java.time.format.DateTimeFormatter;

import com.minble.client.domain.Payment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPagePaymentResponseDto {
    private String name;
    private String createdAt;
    private Integer amount;
    private String expiredAt;
    private String description;

    public MyPagePaymentResponseDto(Payment entity) {
        this.name = entity.getName();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.expiredAt = entity.getCreatedAt().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.amount = entity.getAmount();
        this.description = entity.getDescription();
    }
    
}
