package com.handwoong.rainbowletter.domain.pet.service;

import com.handwoong.rainbowletter.domain.image.model.Image;
import com.handwoong.rainbowletter.domain.image.repository.ImageRepository;
import com.handwoong.rainbowletter.domain.image.service.ImageService;
import com.handwoong.rainbowletter.domain.member.model.Member;
import com.handwoong.rainbowletter.domain.member.repository.MemberRepository;
import com.handwoong.rainbowletter.domain.pet.dto.PetRequest;
import com.handwoong.rainbowletter.domain.pet.dto.PetResponse;
import com.handwoong.rainbowletter.domain.pet.dto.PetResponses;
import com.handwoong.rainbowletter.domain.pet.model.Pet;
import com.handwoong.rainbowletter.domain.pet.repository.PetRepository;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;
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
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void create(final String email, final PetRequest request) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.NOT_FOUND_MEMBER, email));

        final Pet pet = Pet.create(request, member);
        if (Objects.nonNull(request.image())) {
            final Image image = imageRepository.findById(request.image())
                    .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_IMAGE_ID));
            pet.changeImage(image);
        }
        petRepository.save(pet);
    }

    @Override
    public PetResponses findAll(final String email) {
        final List<PetResponse> pets = petRepository.findAllMemberPets(email);
        return new PetResponses(pets);
    }

    @Override
    public PetResponse findById(final String email, final Long petId) {
        return petRepository.findOne(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
    }

    @Override
    @Transactional
    public void edit(final String email, final Long petId, final PetRequest request) {
        final Pet pet = petRepository.findOneMemberPet(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
        pet.update(request);
        if (Objects.nonNull(request.image())) {
            final Image image = imageRepository.findById(request.image())
                    .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_IMAGE_ID));
            pet.changeImage(image);
        }
    }

    @Override
    @Transactional
    public void deleteImage(final String email, final Long petId) {
        final Pet pet = petRepository.findPetWithImage(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
        imageService.remove(pet.getImage());
        pet.removeImage();
    }

    @Override
    @Transactional
    public void delete(final String email, final Long petId) {
        final Pet pet = petRepository.findOneMemberPet(email, petId)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_PET_ID));
        pet.disconnect();
        petRepository.delete(pet);
    }
}
