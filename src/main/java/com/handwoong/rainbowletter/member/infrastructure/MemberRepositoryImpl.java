package com.handwoong.rainbowletter.member.infrastructure;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(MemberEntity.from(member)).toModel();
    }

    @Override
    public Optional<Member> findByEmail(final Email email) {
        return memberJpaRepository.findByEmail(email.toString()).map(MemberEntity::toModel);
    }

    @Override
    public boolean existsByEmail(final Email email) {
        return memberJpaRepository.existsByEmail(email.toString());
    }
}
