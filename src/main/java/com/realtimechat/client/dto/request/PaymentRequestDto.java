package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    private Member member;
    private String name;
    private Integer paid_amount;
    private String pay_method;
    private String imp_uid;
    private String merchant_uid;
    private String buyer_name;
    private String status;
    private String nickname;
    private String description;

    @Builder
    public PaymentRequestDto(Member member, String name, Integer paid_amount, String pay_method, String imp_uid, 
    String merchant_uid, String buyer_name, String status, String nickname, String description) {
        this.member = member;
        this.name = name;
        this.paid_amount = paid_amount;
        this.pay_method = pay_method;
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
        this.buyer_name = buyer_name;
        this.status = status;
        this.nickname = nickname;
        this.description = description;
    }

    public void setMember(Member member) {
        this.member= member;
    }

    public Payment toEntity() {
        return Payment.builder()
                .member(member)
                .name(name)
                .amount(paid_amount)
                .method(pay_method)
                .impUid(imp_uid)
                .merchantUid(merchant_uid)
                .status(status)
                .description(description)
                .build();
    }
    
}
