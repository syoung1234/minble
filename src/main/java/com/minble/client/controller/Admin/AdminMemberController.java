package com.minble.client.controller.Admin;

import com.minble.client.dto.request.admin.AdminMemberRequestDto;
import com.minble.client.dto.response.admin.AdminMemberResponseDto;
import com.minble.client.service.Admin.AdminMemberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    // admin - 회원관리
    @GetMapping
    public ResponseEntity<Page<AdminMemberResponseDto>> list(@RequestParam(value = "searchType", required = false) String searchType,
    @RequestParam(value = "keyword", required = false) String keyword, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AdminMemberResponseDto> response = adminMemberService.list(searchType, keyword, pageable);
        return ResponseEntity.ok(response);
    }

    // admin - 회원관리 상세
    @GetMapping("/{nickname}")
    public ResponseEntity<AdminMemberResponseDto> get(@PathVariable String nickname) {
        AdminMemberResponseDto response = adminMemberService.get(nickname);

        return ResponseEntity.ok(response);
    }

    // admin - 회원 role 변경
    @PostMapping()
    public ResponseEntity<String> update(@RequestBody AdminMemberRequestDto adminMemberRequestDto) {
        String response = adminMemberService.update(adminMemberRequestDto);
        return ResponseEntity.ok(response);
    }
    
}
