package com.handwoong.rainbowletter.member.service.port;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    boolean existsByEmail(Email email);
}
