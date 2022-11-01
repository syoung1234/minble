package com.realtimechat.client.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "member_id")
    private UUID id;
    @Column(length = 100)
    private String email;
    private String passwrod;
    private String nickname;
    private String phone;
    private String role_type;
    private boolean notification;
    
}
