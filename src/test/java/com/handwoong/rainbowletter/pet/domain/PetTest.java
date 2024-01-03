package com.handwoong.rainbowletter.pet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PetTest {
    private Member member;
    private Image image;
    private Favorite favorite;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .id(1L)
                .email(new Email("handwoong@gmail.com"))
                .password(new Password("@password1"))
                .phoneNumber(new PhoneNumber("01011112222"))
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .provider(OAuthProvider.NONE)
                .providerId(OAuthProvider.NONE.name())
                .build();
        image = Image.builder()
                .id(1L)
                .url("http://rainbowletter/image")
                .type(ImageType.PET)
                .objectKey("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .bucket("rainbowletter")
                .build();
        favorite = Favorite.builder()
                .id(1L)
                .total(0)
                .dayIncreaseCount(0)
                .canIncrease(true)
                .lastIncreaseAt(LocalDateTime.now())
                .build();
    }

    @Test
    void 반려동물을_생성한다() {
        // given
        final PetCreate request = PetCreate.builder()
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(1L)
                .build();

        // when
        final Pet pet = Pet.create(member, image, favorite, request);

        // then
        assertThat(pet.id()).isNull();
        assertThat(pet.name()).isEqualTo("두부");
        assertThat(pet.species()).isEqualTo("고양이");
        assertThat(pet.owner()).isEqualTo("형님");
        assertThat(pet.personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(pet.deathAnniversary()).isNull();
        assertThat(pet.image()).isEqualTo(image);
        assertThat(pet.member()).isEqualTo(member);
        assertThat(pet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 반려동물을_업데이트한다() {
        // given
        final Pet pet = Pet.builder()
                .id(1L)
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(image)
                .favorite(favorite)
                .member(member)
                .build();

        final PetUpdate request = PetUpdate.builder()
                .species("호랑이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("잘삐짐")))
                .deathAnniversary(null)
                .image(null)
                .build();

        // when
        final Pet updatePet = pet.update(request, null);

        // then
        assertThat(updatePet.id()).isEqualTo(1);
        assertThat(updatePet.name()).isEqualTo("두부");
        assertThat(updatePet.species()).isEqualTo("호랑이");
        assertThat(updatePet.owner()).isEqualTo("형님");
        assertThat(updatePet.personalities()).containsExactly("잘삐짐");
        assertThat(updatePet.deathAnniversary()).isNull();
        assertThat(updatePet.image()).isNull();
        assertThat(updatePet.member()).isEqualTo(member);
        assertThat(updatePet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 이미지를_삭제한다() {
        // given
        final Pet pet = Pet.builder()
                .id(1L)
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(image)
                .favorite(favorite)
                .member(member)
                .build();

        // when
        final Pet updatePet = pet.removeImage();

        // then
        assertThat(updatePet.id()).isEqualTo(1);
        assertThat(updatePet.name()).isEqualTo("두부");
        assertThat(updatePet.species()).isEqualTo("고양이");
        assertThat(updatePet.owner()).isEqualTo("형님");
        assertThat(updatePet.personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(updatePet.deathAnniversary()).isNull();
        assertThat(updatePet.image()).isNull();
        assertThat(updatePet.member()).isEqualTo(member);
        assertThat(updatePet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 참조중인_객체와의_관계를_끊는다() {
        // given
        final Pet pet = Pet.builder()
                .id(1L)
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(image)
                .favorite(favorite)
                .member(member)
                .build();

        // when
        final Pet clearedPet = pet.clear();

        // then
        assertThat(clearedPet.id()).isEqualTo(1);
        assertThat(clearedPet.name()).isEqualTo("두부");
        assertThat(clearedPet.species()).isEqualTo("고양이");
        assertThat(clearedPet.owner()).isEqualTo("형님");
        assertThat(clearedPet.personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(clearedPet.deathAnniversary()).isNull();
        assertThat(clearedPet.image()).isNull();
        assertThat(clearedPet.member()).isNull();
        assertThat(clearedPet.favorite()).isEqualTo(favorite);
    }
}
