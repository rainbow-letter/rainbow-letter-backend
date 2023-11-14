package com.handwoong.rainbowletter.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.handwoong.rainbowletter.domain.member.Member;

public class CustomUserDetails implements UserDetails {
    private final Member member;
    private final List<GrantedAuthority> roles = new ArrayList<>();

    public CustomUserDetails(final Member member) {
        this.member = member;
        roles.add(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return member.isNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.isNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.isEnabled();
    }
}
