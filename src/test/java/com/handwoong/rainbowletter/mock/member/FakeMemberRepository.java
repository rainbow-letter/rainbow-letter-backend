package com.handwoong.rainbowletter.mock.member;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {
    private final Map<Long, Member> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Member save(final Member member) {
        Long id = Objects.nonNull(member.id()) ? member.id() : sequence++;
        final Member saveMember = createMember(id, member);
        database.put(id, saveMember);
        return saveMember;
    }

    private Member createMember(final Long id, final Member member) {
        return Member.builder()
                .id(id)
                .email(member.email())
                .password(member.password())
                .phoneNumber(member.phoneNumber())
                .role(member.role())
                .status(member.status())
                .provider(member.provider())
                .providerId(member.providerId())
                .build();
    }

    @Override
    public Optional<Member> findByEmail(final Email email) {
        return database.values()
                .stream()
                .filter(member -> member.email().toString().equals(email.toString()))
                .findAny();
    }

    @Override
    public boolean existsByEmail(final Email email) {
        return database.values()
                .stream()
                .anyMatch(member -> member.email().toString().equals(email.toString()));
    }
}
