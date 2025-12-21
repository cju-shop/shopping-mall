package com.cju.shoppingmall.member.service;

import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*  스프링 시큐리티가 아이디로 사용자 정보를 조회할 때 호출되는 서비스 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));

        return new User(
                member.getUsername(),
                member.getPassword(),                   // 지금은 평문
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
    }
}
