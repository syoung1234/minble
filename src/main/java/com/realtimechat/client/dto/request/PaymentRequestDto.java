package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Payment;

import org.json.simple.JSONObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PaymentRequestDto {
    private Member member;
    private String name;
    private Integer paid_amount;
    private String pay_method;
    private String card_name;
    private String imp_uid;
    private String customer_uid;
    private String merchant_uid;
    private String status;
    private String nickname;
    private String description;

    @Builder
    public PaymentRequestDto(Member member, JSONObject paymentData) {
        this.member = member;
        this.name = paymentData.get("name").toString();
        this.paid_amount = (Integer) paymentData.get("paid_amount");
        this.pay_method = paymentData.get("pay_method").toString();
        this.imp_uid = paymentData.get("imp_uid").toString();
        this.customer_uid = paymentData.get("customer_uid").toString();
        this.merchant_uid = paymentData.get("merchant_uid").toString();
        this.status = paymentData.get("status").toString();
    }

    public void setCardName(String card_name) {
        this.card_name = card_name;
    }

    public Payment toEntity() {
        return Payment.builder()
                .member(member)
                .name(name)
                .amount(paid_amount)
                .method(pay_method)
                .cardName(card_name)
                .impUid(imp_uid)
                .merchantUid(merchant_uid)
                .status(status)
                .description(description)
                .build();
    }
    
}
