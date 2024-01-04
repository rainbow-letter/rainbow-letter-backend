package com.handwoong.rainbowletter.letter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.letter.domain.dto.LetterCreate;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LetterTest {
    private Pet pet;
    private Image image;

    @BeforeEach
    void setUp() {
        final Member member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .phoneNumber(new PhoneNumber("01011112222"))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
        final Favorite favorite = Favorite.builder()
                .id(1L)
                .total(0)
                .dayIncreaseCount(0)
                .canIncrease(true)
                .lastIncreaseAt(LocalDateTime.now())
                .build();
        pet = Pet.builder()
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
        image = Image.builder()
                .id(1L)
                .url("http://rainbowletter/image")
                .type(ImageType.PET)
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .bucket("rainbowletter")
                .build();
    }

    @Test
    void 편지를_생성한다() {
        // given
        final LetterCreate request = LetterCreate.builder()
                .summary(new Summary("콩아 잘 지내고 있니?"))
                .content(new Content("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다."))
                .image(1L)
                .build();

        // when
        final Letter letter = Letter.create(request, pet, image);

        // then
        assertThat(letter.id()).isNull();
        assertThat(letter.summary()).hasToString("콩아 잘 지내고 있니?");
        assertThat(letter.content()).hasToString("콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다.");
        assertThat(letter.status()).isEqualTo(LetterStatus.REQUEST);
        assertThat(letter.pet()).isEqualTo(pet);
        assertThat(letter.image()).isEqualTo(image);
        assertThat(letter.reply()).isNull();
        assertThat(letter.createdAt()).isNull();
    }
}
