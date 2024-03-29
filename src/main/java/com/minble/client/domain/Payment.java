package com.minble.client.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    @Column(length = 100)
    private String name;

    private Integer amount;

    @Column(length = 30)
    private String method;

    @Column(length = 30)
    private String cardName;

    @Column(length = 100)
    private String impUid;

    @Column(length = 100)
    private String merchantUid;

    @Column(length = 20)
    private String status;

    private String description;

    public void update(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    
    @Builder
    public Payment(Member member, Subscriber subscriber, String name, Integer amount, String method, String cardName,
    String impUid, String merchantUid, String status, String description) {
        this.member = member;
        this.subscriber = subscriber;
        this.name = name;
        this.amount = amount;
        this.method = method;
        this.cardName = cardName;
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.status = status;
        this.description = description;
    }
}
