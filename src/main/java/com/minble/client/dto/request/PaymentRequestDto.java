package com.minble.client.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minble.client.domain.Member;
import com.minble.client.domain.Payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequestDto {
    private Member member;
    private String name;
    private Integer amount;
    private String pay_method;
    private String card_name;
    private String imp_uid;
    private String customer_uid;
    private String merchant_uid;
    private String status;
    private String nickname;
    private String description;

    @Builder
    public PaymentRequestDto(Member member, String name, Integer amount, String pay_method, String imp_uid, String customer_uid,
    String merchant_uid, String card_name, String status) {
        this.member = member;
        this.name = name;
        this.amount = amount;
        this.pay_method = pay_method;
        this.imp_uid = imp_uid;
        this.customer_uid = customer_uid;
        this.merchant_uid = merchant_uid;
        this.card_name = card_name;
        this.status = status;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Payment toEntity() {
        return Payment.builder()
                .member(member)
                .name(name)
                .amount(amount)
                .method(pay_method)
                .cardName(card_name)
                .impUid(imp_uid)
                .merchantUid(merchant_uid)
                .status(status)
                .description(description)
                .build();
    }
    
}
