package com.handwoong.rainbowletter.mock;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {
    private final Map<Long, Member> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Member save(final Member member) {
        if (member.id() != null) {
            final Member updateMember = Member.builder()
                    .id(member.id())
                    .email(member.email())
                    .password(member.password())
                    .phoneNumber(member.phoneNumber())
                    .role(member.role())
                    .status(member.status())
                    .provider(member.provider())
                    .providerId(member.providerId())
                    .build();
            database.put(member.id(), updateMember);
            return updateMember;
        }
        final Member saveMember = Member.builder()
                .id(sequence)
                .email(member.email())
                .password(member.password())
                .phoneNumber(member.phoneNumber())
                .role(member.role())
                .status(member.status())
                .provider(member.provider())
                .providerId(member.providerId())
                .build();
        database.put(sequence, saveMember);
        sequence++;
        return saveMember;
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
