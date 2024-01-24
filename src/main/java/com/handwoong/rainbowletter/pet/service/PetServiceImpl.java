package com.handwoong.rainbowletter.pet.service;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.service.port.AmazonS3Service;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.pet.controller.port.PetService;
import com.handwoong.rainbowletter.pet.controller.response.DashboardResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3Service amazonS3Service;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public Pet findByEmailAndIdOrElseThrow(final Email email, final Long id) {
        return petRepository.findByEmailAndIdOrElseThrow(email, id);
    }

    @Override
    public List<PetResponse> findAllByEmail(final Email email) {
        return petRepository.findAllByEmail(email);
    }

    @Override
    public List<DashboardResponse> findDashboardItems(final Email email) {
        return petRepository.findDashboardItems(email);
    }

    @Override
    @Transactional
    public Pet create(final Email email, final PetCreate request) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Image image = imageRepository.findByNullableId(request.image());
        final Favorite favorite = favoriteRepository.save(Favorite.create());

        Pet pet = Pet.create(member, image, favorite, request);
        return petRepository.save(pet);
    }

    @Override
    @Transactional
    public Pet update(final Email email, final Long id, final PetUpdate request) {
        final Pet pet = petRepository.findByEmailAndIdOrElseThrow(email, id);
        final Image image = imageRepository.findByNullableId(request.image());
        final Pet updatePet = pet.update(request, image);
        return petRepository.save(updatePet);
    }

    @Override
    @Transactional
    public Pet deleteImage(final Email email, final Long id) {
        final Pet pet = petRepository.findByEmailAndIdWithImageOrElseThrow(email, id);
        assert pet.image() != null;
        amazonS3Service.remove(pet.image().bucket(), pet.image().objectKey());
        final Pet updatePet = pet.removeImage();
        return petRepository.save(updatePet);
    }

    @Override
    @Transactional
    public void delete(final Email email, final Long id) {
        final Pet pet = petRepository.findByEmailAndIdOrElseThrow(email, id);
        final Pet clearedPet = pet.clear();
        petRepository.delete(clearedPet);
    }
}
