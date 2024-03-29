package com.minble.client.controller;

import com.minble.client.config.security.SecurityUser;
import com.minble.client.dto.request.PostRequestDto;
import com.minble.client.dto.response.PostResponseDto;
import com.minble.client.service.PostService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    /**
     * nickname == null 팔로잉한 멤버의 전체 게시글 조회, != null 한 멤버의 게시글 조회
     * @param principal 로그인한 멤버
     * @param nickname star 닉네임
     * @param pageable 페이지
     * @return Page<PostResponseDto>
     */
    @GetMapping()
    public ResponseEntity<Page<PostResponseDto>> list(@AuthenticationPrincipal SecurityUser principal,
                                                      @RequestParam(value = "name", required=false) String nickname, @PageableDefault Pageable pageable) {
        Page<PostResponseDto> response = postService.list(principal.getMember(), nickname, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 게시글 상세 조회
     * @param id : postId
     * @return PostResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> get(@AuthenticationPrincipal SecurityUser principal,
                                               @PathVariable Integer id) {
        PostResponseDto response = postService.find(id, principal.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 게시글 작성
     * @param requestDto (String content, Member member, List<MultipartFile> files, List<String> deleteList)
     * @param principal 작성자
     * @return post id
     */
    @PostMapping(value = "/create")
    public ResponseEntity<Integer> create(@ModelAttribute PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {

        int response = postService.save(requestDto, principal.getMember());

        return ResponseEntity.ok(response);
    }
    
    // 수정 
    @PutMapping("{id}/update")
    public ResponseEntity<String> update(@PathVariable Integer id, @ModelAttribute PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        requestDto.setMember(principal.getMember());
        String response = postService.update(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 삭제
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        String response = postService.delete(id, principal.getMember());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    

}
