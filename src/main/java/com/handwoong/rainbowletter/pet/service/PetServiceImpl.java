package com.handwoong.rainbowletter.pet.service;

import com.handwoong.rainbowletter.favorite.controller.port.FavoriteService;
import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.image.controller.port.ImageService;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.member.controller.port.MemberService;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
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
    private final MemberService memberService;
    private final ImageService imageService;
    private final FavoriteService favoriteService;
    private final PetRepository petRepository;

    @Override
    public Pet findByEmailAndIdOrElseThrow(final Email email, final Long id) {
        return petRepository.findByEmailAndId(email, id)
                .orElseThrow(() -> new PetResourceNotFoundException(id));
    }

    @Override
    public List<Pet> findAllByEmail(final Email email) {
        return petRepository.findAllByEmail(email);
    }

    @Override
    @Transactional
    public Pet create(final Email email, final PetCreate request) {
        final Member member = memberService.findByEmailOrElseThrow(email);
        final Image image = imageService.findById(request.image());
        final Favorite favorite = favoriteService.create();

        Pet pet = Pet.create(member, image, favorite, request);
        return petRepository.save(pet);
    }

    @Override
    @Transactional
    public Pet update(final Email email, final Long id, final PetUpdate request) {
        final Pet pet = findByEmailAndIdOrElseThrow(email, id);
        final Image image = imageService.findById(request.image());
        final Pet updatePet = pet.update(request, image);
        return petRepository.save(updatePet);
    }

    @Override
    @Transactional
    public Pet deleteImage(final Email email, final Long id) {
        final Pet pet = petRepository.findByEmailAndIdWithImage(email, id)
                .orElseThrow(() -> new PetResourceNotFoundException(id));
        imageService.remove(pet.image());
        final Pet updatePet = pet.removeImage();
        return petRepository.save(updatePet);
    }

    @Override
    @Transactional
    public void delete(final Email email, final Long id) {
        final Pet pet = findByEmailAndIdOrElseThrow(email, id);
        final Pet clearedPet = pet.clear();
        petRepository.delete(clearedPet);
    }
}
