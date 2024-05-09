package com.handwoong.rainbowletter.temporary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import com.handwoong.rainbowletter.temporary.controller.port.TemporaryService;
import com.handwoong.rainbowletter.temporary.domain.Temporary;
import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryCreate;
import com.handwoong.rainbowletter.temporary.domain.dto.TemporaryUpdate;
import com.handwoong.rainbowletter.temporary.service.port.TemporaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemporaryServiceImpl implements TemporaryService {

    private final TemporaryRepository temporaryRepository;
    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final UuidGenerator uuidGenerator;

    @Override
    @Transactional
    public String create(final Email email, final TemporaryCreate temporaryCreate) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        petRepository.findByEmailAndIdOrElseThrow(email, temporaryCreate.petId());
        final Temporary temporary = Temporary.create(member.id(), temporaryCreate, uuidGenerator);
        return temporaryRepository.save(temporary).sessionId();
    }

    @Override
    @Transactional
    public void delete(final Email email, final Long id) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Temporary temporary = temporaryRepository.findByIdAndMemberIdOrElseThrow(id, member.id());
        temporaryRepository.save(temporary.delete());
    }

    @Override
    @Transactional
    public String changeSession(final Email email, final Long id) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Temporary temporary = temporaryRepository.findByIdAndMemberIdOrElseThrow(id, member.id());
        return temporaryRepository.save(temporary.changeSession(uuidGenerator)).sessionId();
    }

    @Override
    @Transactional
    public void update(final Email email, final TemporaryUpdate temporaryUpdate) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Temporary temporary =
                temporaryRepository.findByIdAndMemberIdOrElseThrow(temporaryUpdate.id(), member.id());
        temporaryRepository.save(temporary.update(temporaryUpdate));
    }

    @Override
    public boolean exists(final Email email) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        return temporaryRepository.existsByMemberId(member.id());
    }

    @Override
    public Temporary findByEmail(final Email email) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        return temporaryRepository.findByMemberId(member.id());
    }
}
