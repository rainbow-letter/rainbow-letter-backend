package com.handwoong.rainbowletter.pet.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.infrastructure.FavoriteEntity;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.infrastructure.ImageEntity;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.member.infrastructure.MemberEntity;
import com.handwoong.rainbowletter.pet.domain.Pet;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PetEntityTest {
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
    void 반려동물_도메인으로_엔티티를_생성한다() {
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
        final PetEntity petEntity = PetEntity.from(pet);

        // then
        assertThat(petEntity.getId()).isEqualTo(1);
        assertThat(petEntity.getName()).isEqualTo("두부");
        assertThat(petEntity.getSpecies()).isEqualTo("고양이");
        assertThat(petEntity.getOwner()).isEqualTo("형님");
        assertThat(petEntity.getPersonalities()).containsExactly("질투쟁이", "새침함");
        assertThat(petEntity.getDeathAnniversary()).isNull();
        assertThat(petEntity.getImageEntity()).isEqualTo(ImageEntity.from(image));
        assertThat(petEntity.getMemberEntity()).isEqualTo(MemberEntity.from(member));
        assertThat(petEntity.getFavoriteEntity()).isEqualTo(FavoriteEntity.from(favorite));
    }

    @Test
    void 엔티티로_반려동물_도메인을_생성한다() {
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
        final PetEntity petEntity = PetEntity.from(pet);

        // when
        final Pet convertPet = petEntity.toModel();

        // then
        assertThat(convertPet.id()).isEqualTo(1);
        assertThat(convertPet.name()).isEqualTo("두부");
        assertThat(convertPet.species()).isEqualTo("고양이");
        assertThat(convertPet.owner()).isEqualTo("형님");
        assertThat(convertPet.personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(convertPet.deathAnniversary()).isNull();
        assertThat(convertPet.image()).isEqualTo(image);
        assertThat(convertPet.member()).isEqualTo(member);
        assertThat(convertPet.favorite()).isEqualTo(favorite);
    }
}
