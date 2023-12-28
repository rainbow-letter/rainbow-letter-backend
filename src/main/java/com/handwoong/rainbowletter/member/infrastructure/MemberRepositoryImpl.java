package com.handwoong.rainbowletter.member.infrastructure;

import static com.handwoong.rainbowletter.member.infrastructure.QMemberEntity.memberEntity;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final JPAQueryFactory queryFactory;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(final Member member) {
        memberJpaRepository.save(MemberEntity.fromModel(member));
    }

    @Override
    public Optional<Member> findByEmail(final Email email) {
        return memberJpaRepository.findByEmail(email.toString()).map(MemberEntity::toModel);
    }

    @Override
    public Optional<Member> findInfoByEmail(final Email email) {
        final QMemberEntity m = memberEntity;
        return Optional.ofNullable(
                queryFactory.select(Projections.fields(Member.class, m.id, m.email, m.phoneNumber, m.role))
                        .from(m)
                        .where(m.email.eq(email.toString()))
                        .fetchFirst());
    }

    @Override
    public boolean existsByEmail(final Email email) {
        return memberJpaRepository.existsByEmail(email.toString());
    }
}
