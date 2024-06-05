package com.handwoong.rainbowletter.mock.letter;

import java.time.LocalDateTime;
import java.util.List;

import com.handwoong.rainbowletter.common.config.client.ClientConfig;
import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;
import com.handwoong.rainbowletter.letter.controller.port.LetterService;
import com.handwoong.rainbowletter.letter.controller.port.ReplyService;
import com.handwoong.rainbowletter.letter.service.LetterServiceImpl;
import com.handwoong.rainbowletter.letter.service.ReplyServiceImpl;
import com.handwoong.rainbowletter.letter.service.port.LetterRepository;
import com.handwoong.rainbowletter.letter.service.port.ReplyRepository;
import com.handwoong.rainbowletter.mail.service.MailServiceImpl;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.mock.common.FakeUuidGenerator;
import com.handwoong.rainbowletter.mock.image.ImageTestContainer;
import com.handwoong.rainbowletter.mock.mail.FakeMailRepository;
import com.handwoong.rainbowletter.mock.mail.FakeMailSender;
import com.handwoong.rainbowletter.mock.mail.FakeMailTemplateManager;
import com.handwoong.rainbowletter.mock.notification.FakeMessageSender;
import com.handwoong.rainbowletter.mock.pet.PetTestContainer;
import com.handwoong.rainbowletter.notification.application.NotificationServiceImpl;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.service.port.PetRepository;

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
            .personalities("질투쟁이,새침함")
            .deathAnniversary(null)
            .image(image)
            .favorite(favorite)
            .member(member)
            .build();

    public final LetterRepository repository;
    public final LetterService service;
    public final PetRepository petRepository;
    public final ImageRepository imageRepository;
    public final ReplyRepository replyRepository;
    public final ReplyService replyService;

    public LetterTestContainer() {
        final PetTestContainer petTestContainer = new PetTestContainer();
        final ImageTestContainer imageTestContainer = new ImageTestContainer();

        this.petRepository = petTestContainer.repository;
        this.imageRepository = imageTestContainer.repository;
        imageRepository.save(image);
        petRepository.save(pet);

        this.repository = new FakeLetterRepository();
        this.service = new LetterServiceImpl(new FakeUuidGenerator(), petRepository, imageRepository, repository);

        this.replyRepository = new FakeReplyRepository();
        final MailServiceImpl mailService = new MailServiceImpl(
                new FakeMailSender(), new FakeMailTemplateManager("제목", "본문"), new FakeMailRepository());
        final NotificationServiceImpl notificationService = new NotificationServiceImpl(new FakeMessageSender());
        final ClientConfig clientConfig = new ClientConfig(List.of("http://localhost"));
        this.replyService = new ReplyServiceImpl(notificationService, mailService, replyRepository, repository,
                clientConfig);
    }
}
