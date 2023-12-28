package com.handwoong.rainbowletter.pet.service;

import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.RainbowLetterException;
import com.handwoong.rainbowletter.image.infrastructure.ImageEntity;
import com.handwoong.rainbowletter.image.infrastructure.ImageJpaRepository;
import com.handwoong.rainbowletter.image.service.ImageService;
import com.handwoong.rainbowletter.member.infrastructure.MemberEntity;
import com.handwoong.rainbowletter.member.infrastructure.MemberJpaRepository;
import com.handwoong.rainbowletter.pet.controller.response.PetListResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.domain.dto.PetRequest;
import com.handwoong.rainbowletter.pet.infrastructure.PetRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final ImageJpaRepository imageJpaRepository;
    private final ImageService imageService;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    @Transactional
    public void create(final String email, final PetRequest request) {
        final MemberEntity memberEntity = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.NOT_FOUND_MEMBER, email));

        final com.handwoong.rainbowletter.pet.infrastructure.Pet pet = com.handwoong.rainbowletter.pet.infrastructure.Pet.create(
                request, memberEntity);
        if (Objects.nonNull(request.image())) {
            final ImageEntity imageEntity = imageJpaRepository.findById(request.image())
                    .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_IMAGE_ID));
            pet.changeImage(imageEntity);
        }
        petRepository.save(pet);
    }

    @Override
    public PetListResponse findAll(final String email) {
        final List<PetResponse> petResponses = petRepository.findAllMemberPets(email);
        return new PetListResponse(petResponses);
    }

    @Override
    public PetResponse findById(final String email, final Long petId) {
        return petRepository.findOne(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
    }

    @Override
    @Transactional
    public void edit(final String email, final Long petId, final PetRequest request) {
        final com.handwoong.rainbowletter.pet.infrastructure.Pet pet = petRepository.findOneMemberPet(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
        pet.update(request);
        if (Objects.nonNull(request.image())) {
            final ImageEntity imageEntity = imageJpaRepository.findById(request.image())
                    .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_IMAGE_ID));
            pet.changeImage(imageEntity);
        }
    }

    @Override
    @Transactional
    public void deleteImage(final String email, final Long petId) {
        final com.handwoong.rainbowletter.pet.infrastructure.Pet pet = petRepository.findPetWithImage(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
        imageService.remove(pet.getImageEntity());
        pet.removeImage();
    }

    @Override
    @Transactional
    public void delete(final String email, final Long petId) {
        final com.handwoong.rainbowletter.pet.infrastructure.Pet pet = petRepository.findOneMemberPet(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
        pet.disconnect();
        petRepository.delete(pet);
    }
}
