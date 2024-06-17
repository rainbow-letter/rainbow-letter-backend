package com.handwoong.rainbowletter.mock.pet;

import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.mock.favorite.FavoriteTestContainer;
import com.handwoong.rainbowletter.mock.image.ImageTestContainer;
import com.handwoong.rainbowletter.mock.member.MemberTestContainer;
import com.handwoong.rainbowletter.pet.controller.port.PetService;
import com.handwoong.rainbowletter.pet.service.PetServiceImpl;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;

public class PetTestContainer {
    public final MemberRepository memberRepository;
    public final ImageRepository imageRepository;
    public final FavoriteRepository favoriteRepository;
    public final PetRepository repository;
    public final PetService service;

    public PetTestContainer() {
        final MemberTestContainer memberTestContainer = new MemberTestContainer();
        final ImageTestContainer imageTestContainer = new ImageTestContainer();
        this.memberRepository = memberTestContainer.repository;
        this.imageRepository = imageTestContainer.repository;

        this.repository = new FakePetRepository();
        this.favoriteRepository = new FavoriteTestContainer().repository;
        this.service = new PetServiceImpl(
                repository, imageRepository, memberRepository, favoriteRepository);
    }
}
