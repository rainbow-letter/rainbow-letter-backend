package com.handwoong.rainbowletter.pet.service;

import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.service.ImageService;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.service.MemberService;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import com.handwoong.rainbowletter.pet.exception.PetResourceNotFoundException;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {
    private final ImageService imageService;
    private final MemberService memberService;
    private final PetRepository petRepository;

    @Override
    public Pet findByIdOrElseThrow(final Email email, final Long id) {
        return petRepository.findById(email, id)
                .orElseThrow(() -> new PetResourceNotFoundException(id));
    }

    @Override
    public Pet findById(final String email, final Long id) {
        return findByIdOrElseThrow(new Email(email), id);
    }

    @Override
    public List<Pet> findAll(final String email) {
        return petRepository.findAll(new Email(email));
    }

    @Override
    @Transactional
    public void create(final String email, final PetCreate request) {
        final Member member = memberService.findByEmailOrElseThrow(new Email(email));
        final Image image = imageService.findById(request.image());
        Pet pet = Pet.create(member, request, image);
        petRepository.save(pet);
    }

    @Override
    @Transactional
    public void update(final String email, final Long id, final PetUpdate request) {
        final Pet pet = findByIdOrElseThrow(new Email(email), id);
        final Image image = imageService.findById(request.image());
        final Pet updatePet = pet.update(request, image);
        petRepository.save(updatePet);
    }

    @Override
    @Transactional
    public void deleteImage(final String email, final Long id) {
        final Pet pet = petRepository.findByIdWithImage(new Email(email), id)
                .orElseThrow(() -> new PetResourceNotFoundException(id));
        imageService.remove(pet.image());
        final Pet updatePet = pet.removeImage();
        petRepository.save(updatePet);
    }

    @Override
    @Transactional
    public void delete(final String email, final Long id) {
        final Pet pet = findByIdOrElseThrow(new Email(email), id);
        final Pet disconnectedPet = pet.disconnectRelationShip();
        petRepository.delete(disconnectedPet);
    }
}
