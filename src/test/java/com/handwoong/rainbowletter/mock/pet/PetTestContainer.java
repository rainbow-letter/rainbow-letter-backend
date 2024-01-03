package com.handwoong.rainbowletter.mock.pet;

import com.handwoong.rainbowletter.favorite.controller.port.FavoriteService;
import com.handwoong.rainbowletter.image.controller.port.ImageService;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.member.controller.port.MemberService;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.mock.favorite.FavoriteTestContainer;
import com.handwoong.rainbowletter.mock.image.ImageTestContainer;
import com.handwoong.rainbowletter.mock.member.MemberTestContainer;
import com.handwoong.rainbowletter.pet.service.PetService;
import com.handwoong.rainbowletter.pet.service.PetServiceImpl;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;

public class PetTestContainer {
    public final MemberRepository memberRepository;
    public final ImageRepository imageRepository;
    public final FavoriteService favoriteService;
    public final PetRepository repository;
    public final PetService service;

    public PetTestContainer() {
        final MemberTestContainer memberTestContainer = new MemberTestContainer();
        final ImageTestContainer imageTestContainer = new ImageTestContainer();
        final MemberService memberService = memberTestContainer.service;
        final ImageService imageService = imageTestContainer.service;
        this.memberRepository = memberTestContainer.repository;
        this.imageRepository = imageTestContainer.repository;

        this.repository = new FakePetRepository();
        this.favoriteService = new FavoriteTestContainer().service;
        this.service = new PetServiceImpl(memberService, imageService, favoriteService, repository);
    }
}
