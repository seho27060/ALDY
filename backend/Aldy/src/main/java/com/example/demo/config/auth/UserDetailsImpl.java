package com.example.demo.config.auth;

import com.example.demo.domain.entity.Member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String baeckjoonId;
    private String nickname;
    private String password;

    private GrantedAuthority authority;

    public UserDetailsImpl(Member member){
        this.id = member.getId();
        this.baeckjoonId = member.getBaekjoonId();
        this.nickname = member.getNickname();
        this.password = member.getPassword();
        this.authority = new SimpleGrantedAuthority("USER_ROLE");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return baeckjoonId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
