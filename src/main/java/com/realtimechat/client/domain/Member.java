package com.realtimechat.client.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "member_id", columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;

    @Column(length = 100, unique = true, nullable = false)
    private String email;
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String phone;
    private String profile_path;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean notification;

    
}
