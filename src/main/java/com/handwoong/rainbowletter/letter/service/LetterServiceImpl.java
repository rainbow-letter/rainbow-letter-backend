package com.handwoong.rainbowletter.letter.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.controller.request.ReplyTypeRequest;
import com.handwoong.rainbowletter.letter.controller.response.LetterAdminResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.CreateReply;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LetterServiceImpl implements LetterService {
    private final UuidGenerator uuidGenerator;
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;
    private final LetterRepository letterRepository;

    @Override
    @CreateReply
    @Transactional
    public Letter create(final Long petId, final LetterCreate request) {
        final Pet pet = petRepository.findByIdOrElseThrow(petId);
        final Image image = imageRepository.findByNullableId(request.image());
        final Letter letter = Letter.create(request, pet, image, uuidGenerator);
        return letterRepository.save(letter);
    }

    @Override
    public List<LetterBoxResponse> findAllLetterBoxByEmail(final Email email) {
        return letterRepository.findAllLetterBoxByEmail(email);
    }

    @Override
    public LetterResponse findLetterById(final Email email, final Long id) {
        return letterRepository.findLetterResponseByIdOrElseThrow(email, id);
    }

    @Override
    public Page<LetterAdminResponse> findAllAdminLetters(final ReplyTypeRequest type,
                                                         final LocalDate startDate,
                                                         final LocalDate endDate,
                                                         final String email,
                                                         final Boolean inspect,
                                                         final Pageable pageable) {
        return letterRepository.findAdminAllLetterResponses(type, startDate, endDate, email, inspect, pageable);
    }

    @Override
    public LetterResponse findLetterByShareLink(final String shareLink) {
        return letterRepository.findLetterResponseByShareLinkOrElseThrow(shareLink);
    }
}
