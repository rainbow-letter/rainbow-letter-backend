package com.handwoong.rainbowletter.mock.letter;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.service.LetterServiceImpl;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.mock.image.ImageTestContainer;
import com.handwoong.rainbowletter.mock.pet.PetTestContainer;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public class LetterTestContainer {
    public final Member member = Member.builder()
            .id(1L)
            .email(new Email("handwoong@gmail.com"))
            .password(new Password("@password1"))
            .phoneNumber(new PhoneNumber("01011112222"))
            .role(MemberRole.ROLE_USER)
            .status(MemberStatus.ACTIVE)
            .provider(OAuthProvider.NONE)
            .providerId(OAuthProvider.NONE.name())
            .build();
    public final Favorite favorite = Favorite.builder()
            .id(1L)
            .total(0)
            .dayIncreaseCount(0)
            .canIncrease(true)
            .lastIncreaseAt(LocalDateTime.now())
            .build();
    public final Image image = Image.builder()
            .id(1L)
            .url("http://rainbowletter/image")
            .type(ImageType.PET)
            .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
            .bucket("rainbowletter")
            .build();
    public final Pet pet = Pet.builder()
            .id(1L)
            .name("콩이")
            .species("고양이")
            .owner("엄마")
            .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
            .deathAnniversary(null)
            .image(image)
            .favorite(favorite)
            .member(member)
            .build();

    public final LetterRepository repository;
    public final LetterService service;
    public final PetRepository petRepository;
    public final ImageRepository imageRepository;

    public LetterTestContainer() {
        final PetTestContainer petTestContainer = new PetTestContainer();
        final ImageTestContainer imageTestContainer = new ImageTestContainer();

        this.petRepository = petTestContainer.repository;
        this.imageRepository = imageTestContainer.repository;
        imageRepository.save(image);
        petRepository.save(pet);

        this.repository = new FakeLetterRepository();
        this.service = new LetterServiceImpl(petRepository, imageRepository, repository);
    }
}
