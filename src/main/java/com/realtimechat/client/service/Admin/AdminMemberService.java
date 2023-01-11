package com.realtimechat.client.service.Admin;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.response.admin.AdminMemberResponseDto;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;

    public Page<AdminMemberResponseDto> list(Pageable pageable) {
        Page<Member> memberList = memberRepository.findAll(pageable);

        Page<AdminMemberResponseDto> adminMemberResponseDto = memberList.map(member -> new AdminMemberResponseDto(member));

        return adminMemberResponseDto;
    }
    
}
