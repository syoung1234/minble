package com.realtimechat.client.controller.Admin;

import com.realtimechat.client.dto.response.admin.AdminMemberResponseDto;
import com.realtimechat.client.service.Admin.AdminMemberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping
    public Page<AdminMemberResponseDto> list(Pageable pageable) {
        return adminMemberService.list(pageable);
    }
    
}
