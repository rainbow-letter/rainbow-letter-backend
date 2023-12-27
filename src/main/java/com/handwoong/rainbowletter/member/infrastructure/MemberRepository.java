package com.handwoong.rainbowletter.member.infrastructure;

import com.handwoong.rainbowletter.member.controller.response.MemberResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(final String email);

    MemberResponse findInfoByEmail(final String email);

    boolean existsByEmail(final String email);
}
