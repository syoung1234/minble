package com.realtimechat.client.controller.Admin;

import com.realtimechat.client.dto.request.admin.AdminMemberRequestDto;
import com.realtimechat.client.dto.response.admin.AdminMemberResponseDto;
import com.realtimechat.client.service.Admin.AdminMemberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    // admin - 회원관리
    @GetMapping
    public ResponseEntity<Page<AdminMemberResponseDto>> list(Pageable pageable) {
        Page<AdminMemberResponseDto> response = adminMemberService.list(pageable);
        return ResponseEntity.ok(response);
    }

    // admin - 회원관리 상세
    @GetMapping("/{nickname}")
    public ResponseEntity<AdminMemberResponseDto> get(@PathVariable String nickname) {
        AdminMemberResponseDto response = adminMemberService.get(nickname);

        return ResponseEntity.ok(response);
    }

    
}
