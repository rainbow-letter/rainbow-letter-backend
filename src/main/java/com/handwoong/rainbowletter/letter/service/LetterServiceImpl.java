package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponse;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.CreateReply;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LetterServiceImpl implements LetterService {
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;
    private final LetterRepository letterRepository;

    @Override
    @CreateReply
    @Transactional
    public Letter create(final Long petId, final LetterCreate request) {
        final Pet pet = petRepository.findByIdOrElseThrow(petId);
        final Image image = imageRepository.findByNullableId(request.image());
        final Letter letter = Letter.create(request, pet, image);
        return letterRepository.save(letter);
    }

    @Override
    public List<LetterBoxResponse> findAllLetterBoxByEmail(final Email email) {
        return letterRepository.findAllLetterBoxByEmail(email);
    }

    @Override
    public LetterResponse findLetterById(final Long id) {
        return letterRepository.findLetterResponseByIdOrElseThrow(id);
    }
}
