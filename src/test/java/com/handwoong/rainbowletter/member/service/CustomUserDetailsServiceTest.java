package com.handwoong.rainbowletter.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.mock.TestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsServiceTest {
    @Test
    void 이메일로_회원을_찾으면_UserDetails를_생성한다() {
        // given
        final TestContainer testContainer = new TestContainer();

        final Member member = Member.builder()
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1").encode(testContainer.passwordEncoder))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .build();
        testContainer.memberRepository.save(member);

        final CustomUserDetailsService userDetailsService =
                new CustomUserDetailsService(testContainer.memberRepository);

        // when
        final UserDetails userDetails = userDetailsService.loadUserByUsername("handwoong@gmail.com");

        // then
        assertThat(userDetails.getUsername()).isEqualTo("handwoong@gmail.com");
        assertThat(userDetails.getPassword()).isEqualTo("@password1" + "$$$!!@@@");
        assertThat(userDetails.getAuthorities()).extracting("role").containsExactly(MemberRole.ROLE_USER.name());
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void 이메일로_회원을_찾지_못하면_예외가_발생한다() {
        // given
        final TestContainer testContainer = new TestContainer();
        final CustomUserDetailsService userDetailsService =
                new CustomUserDetailsService(testContainer.memberRepository);

        // when
        // then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("handwoong@gmail.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다. - handwoong@gmail.com");
    }
}
