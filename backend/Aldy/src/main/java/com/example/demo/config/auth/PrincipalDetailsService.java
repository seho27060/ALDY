package com.example.demo.config.auth;

import com.example.demo.domain.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String backjoonId) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        Optional<Member> member = memberRepository.findByBackjoonId(backjoonId);

        return new PrincipalDetails(member.get());
    }
}
