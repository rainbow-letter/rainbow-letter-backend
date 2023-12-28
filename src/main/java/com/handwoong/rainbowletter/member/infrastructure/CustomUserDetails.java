package com.handwoong.rainbowletter.member.infrastructure;

import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomUserDetails implements UserDetails, OAuth2User {
    private final transient Member member;
    private final List<GrantedAuthority> roles = new ArrayList<>();
    private transient Map<String, Object> attributes;

    public CustomUserDetails(final Member member) {
        this.member = member;
        roles.add(new SimpleGrantedAuthority(member.role().name()));
    }

    public CustomUserDetails(final Member member, final Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
        roles.add(new SimpleGrantedAuthority(member.role().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return member.password().toString();
    }

    @Override
    public String getUsername() {
        return member.email().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return member.isStatusMismatch(MemberStatus.SLEEP);
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.isStatusMismatch(MemberStatus.LOCK);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return member.isStatusMismatch(MemberStatus.INACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return member.isStatusMismatch(MemberStatus.LEAVE);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return member.email().toString();
    }
}
