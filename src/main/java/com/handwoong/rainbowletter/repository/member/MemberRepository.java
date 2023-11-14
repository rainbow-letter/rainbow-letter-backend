package com.handwoong.rainbowletter.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.handwoong.rainbowletter.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(final String email);

    boolean existsByEmail(final String email);
}
