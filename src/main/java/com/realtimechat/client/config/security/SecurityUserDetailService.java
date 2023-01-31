package com.realtimechat.client.config.security;

import java.util.Optional;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optional = memberRepository.findByNickname(username);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(username + " 사용자 없음");
        } else {
            Member member = optional.get();
            return new SecurityUser(member);
        }
        // return (UserDetails) memberRepository.findByEmail(username)
        //         .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
    
}
