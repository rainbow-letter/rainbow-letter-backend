package com.handwoong.rainbowletter.letter.service;

import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
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
    public Letter create(final Long petId, final LetterCreate request) {
        return null;
    }
}
