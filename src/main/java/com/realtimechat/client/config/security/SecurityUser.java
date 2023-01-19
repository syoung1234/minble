package com.realtimechat.client.config.security;


import com.realtimechat.client.domain.Member;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private Member member;

    public SecurityUser(Member member) {
        super(member.getId().toString(), member.getNickname(),
        AuthorityUtils.createAuthorityList(member.getRole().toString()));
        this.member = member;
    }
    
    public Member getMember() {
        return member;
    }
}
