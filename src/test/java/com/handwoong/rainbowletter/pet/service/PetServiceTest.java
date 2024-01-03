package com.handwoong.rainbowletter.pet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import com.handwoong.rainbowletter.member.exception.MemberEmailNotFoundException;
import com.handwoong.rainbowletter.mock.pet.PetTestContainer;
import com.handwoong.rainbowletter.pet.domain.Pet;
import com.handwoong.rainbowletter.pet.domain.dto.PetCreate;
import com.handwoong.rainbowletter.pet.domain.dto.PetUpdate;
import com.handwoong.rainbowletter.pet.exception.PetResourceNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PetServiceTest {
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
                .lastIncreaseAt(LocalDate.now().atStartOfDay())
                .build();
    }

    @Test
    void 이메일과_ID로_반려동물을_찾는다() {
        // given
        final Pet pet = Pet.builder()
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(image)
                .favorite(favorite)
                .member(member)
                .build();

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.repository.save(pet);

        // when
        final Pet findPet = testContainer.service
                .findByEmailAndIdOrElseThrow(new Email("handwoong@gmail.com"), 1L);

        // then
        assertThat(findPet.id()).isEqualTo(1);
        assertThat(findPet.name()).isEqualTo("두부");
        assertThat(findPet.species()).isEqualTo("고양이");
        assertThat(findPet.owner()).isEqualTo("형님");
        assertThat(findPet.personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(findPet.deathAnniversary()).isNull();
        assertThat(findPet.image()).isEqualTo(image);
        assertThat(findPet.member()).isEqualTo(member);
        assertThat(findPet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 이메일과_ID로_반려동물을_찾지_못하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final PetTestContainer testContainer = new PetTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service
                .findByEmailAndIdOrElseThrow(email, 1L))
                .isInstanceOf(PetResourceNotFoundException.class)
                .hasMessage("해당 반려 동물을 찾을 수 없습니다.");
    }

    @Test
    void 이메일로_반려동물_목록을_조회한다() {
        // given
        final Pet pet1 = Pet.builder()
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(image)
                .favorite(favorite)
                .member(member)
                .build();
        final Pet pet2 = Pet.builder()
                .name("침이")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("애교쟁이")))
                .deathAnniversary(null)
                .image(image)
                .favorite(favorite)
                .member(member)
                .build();

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.repository.save(pet1);
        testContainer.repository.save(pet2);

        // when
        final List<Pet> pets = testContainer.service.findAllByEmail(new Email("handwoong@gmail.com"));

        // then
        assertThat(pets).hasSize(2);
        assertThat(pets.get(0).id()).isEqualTo(1);
        assertThat(pets.get(0).name()).isEqualTo("두부");
        assertThat(pets.get(0).species()).isEqualTo("고양이");
        assertThat(pets.get(0).owner()).isEqualTo("형님");
        assertThat(pets.get(0).personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(pets.get(0).deathAnniversary()).isNull();
        assertThat(pets.get(0).image()).isEqualTo(image);
        assertThat(pets.get(0).member()).isEqualTo(member);
        assertThat(pets.get(0).favorite()).isEqualTo(favorite);

        assertThat(pets.get(1).id()).isEqualTo(2);
        assertThat(pets.get(1).name()).isEqualTo("침이");
        assertThat(pets.get(1).species()).isEqualTo("고양이");
        assertThat(pets.get(1).owner()).isEqualTo("형님");
        assertThat(pets.get(1).personalities()).containsExactly("애교쟁이");
        assertThat(pets.get(1).deathAnniversary()).isNull();
        assertThat(pets.get(1).image()).isEqualTo(image);
        assertThat(pets.get(1).member()).isEqualTo(member);
        assertThat(pets.get(1).favorite()).isEqualTo(favorite);
    }

    @Test
    void 이미지가_없는_반려동물을_생성한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final PetCreate request = PetCreate.builder()
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(null)
                .build();

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.memberRepository.save(member);

        // when
        final Pet pet = testContainer.service.create(email, request);

        // then
        assertThat(pet.id()).isEqualTo(1);
        assertThat(pet.name()).isEqualTo("두부");
        assertThat(pet.species()).isEqualTo("고양이");
        assertThat(pet.owner()).isEqualTo("형님");
        assertThat(pet.personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(pet.deathAnniversary()).isNull();
        assertThat(pet.image()).isNull();
        assertThat(pet.member()).isEqualTo(member);
        assertThat(pet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 이미지가_있는_반려동물을_생성한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final PetCreate request = PetCreate.builder()
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(1L)
                .build();

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.memberRepository.save(member);
        testContainer.imageRepository.save(image);

        // when
        final Pet pet = testContainer.service.create(email, request);

        // then
        assertThat(pet.id()).isEqualTo(1);
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
    void 반려동물_생성시_회원을_찾지_못하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final PetCreate request = PetCreate.builder()
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(1L)
                .build();

        final PetTestContainer testContainer = new PetTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service.create(email, request))
                .isInstanceOf(MemberEmailNotFoundException.class)
                .hasMessage("해당 회원을 찾을 수 없습니다.");
    }

    @Test
    void 이미지없이_반려동물을_업데이트한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
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

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.repository.save(pet);

        // when
        final Pet updatedPet = testContainer.service.update(email, 1L, request);

        // then
        assertThat(updatedPet.id()).isEqualTo(1);
        assertThat(updatedPet.name()).isEqualTo("두부");
        assertThat(updatedPet.species()).isEqualTo("호랑이");
        assertThat(updatedPet.owner()).isEqualTo("형님");
        assertThat(updatedPet.personalities()).containsExactly("잘삐짐");
        assertThat(updatedPet.deathAnniversary()).isNull();
        assertThat(updatedPet.image()).isNull();
        assertThat(updatedPet.member()).isEqualTo(member);
        assertThat(updatedPet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 이미지를_포함해_반려동물을_업데이트한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
        final Pet pet = Pet.builder()
                .id(1L)
                .name("두부")
                .species("고양이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("질투쟁이", "새침함")))
                .deathAnniversary(null)
                .image(null)
                .favorite(favorite)
                .member(member)
                .build();

        final PetUpdate request = PetUpdate.builder()
                .species("호랑이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("잘삐짐")))
                .deathAnniversary(null)
                .image(1L)
                .build();

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.imageRepository.save(image);
        testContainer.repository.save(pet);

        // when
        final Pet updatedPet = testContainer.service.update(email, 1L, request);

        // then
        assertThat(updatedPet.id()).isEqualTo(1);
        assertThat(updatedPet.name()).isEqualTo("두부");
        assertThat(updatedPet.species()).isEqualTo("호랑이");
        assertThat(updatedPet.owner()).isEqualTo("형님");
        assertThat(updatedPet.personalities()).containsExactly("잘삐짐");
        assertThat(updatedPet.deathAnniversary()).isNull();
        assertThat(updatedPet.image()).isEqualTo(image);
        assertThat(updatedPet.member()).isEqualTo(member);
        assertThat(updatedPet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 반려동물_업데이트시_이메일과_ID로_반려동물을_찾지_못하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@naver.com");

        final PetUpdate request = PetUpdate.builder()
                .species("호랑이")
                .owner("형님")
                .personalities(new HashSet<>(List.of("잘삐짐")))
                .deathAnniversary(null)
                .image(null)
                .build();

        final PetTestContainer testContainer = new PetTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service.update(email, 1L, request))
                .isInstanceOf(PetResourceNotFoundException.class)
                .hasMessage("해당 반려 동물을 찾을 수 없습니다.");
    }

    @Test
    void 반려동물의_이미지를_삭제한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
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

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.repository.save(pet);

        // when
        final Pet deletedImagePet = testContainer.service.deleteImage(email, 1L);

        // then
        assertThat(deletedImagePet.id()).isEqualTo(1);
        assertThat(deletedImagePet.name()).isEqualTo("두부");
        assertThat(deletedImagePet.species()).isEqualTo("고양이");
        assertThat(deletedImagePet.owner()).isEqualTo("형님");
        assertThat(deletedImagePet.personalities()).containsExactly("질투쟁이", "새침함");
        assertThat(deletedImagePet.deathAnniversary()).isNull();
        assertThat(deletedImagePet.image()).isNull();
        assertThat(deletedImagePet.member()).isEqualTo(member);
        assertThat(deletedImagePet.favorite()).isEqualTo(favorite);
    }

    @Test
    void 반려동물의_이미지_삭제시_ID로_이미지를_가진_반려동물을_찾지_못하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");

        final PetTestContainer testContainer = new PetTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service.deleteImage(email, 1L))
                .isInstanceOf(PetResourceNotFoundException.class)
                .hasMessage("해당 반려 동물을 찾을 수 없습니다.");
    }

    @Test
    void 반려동물을_삭제한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");
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

        final PetTestContainer testContainer = new PetTestContainer();
        testContainer.repository.save(pet);

        // when
        testContainer.service.delete(email, 1L);

        // then
        assertThat(testContainer.repository.findByEmailAndId(email, 1L).isEmpty()).isTrue();
    }

    @Test
    void 반려동물을_삭제시_이메일과_ID로_반려동물을_찾지_못하면_예외가_발생한다() {
        // given
        final Email email = new Email("handwoong@gmail.com");

        final PetTestContainer testContainer = new PetTestContainer();

        // when
        // then
        assertThatThrownBy(() -> testContainer.service.delete(email, 1L))
                .isInstanceOf(PetResourceNotFoundException.class)
                .hasMessage("해당 반려 동물을 찾을 수 없습니다.");
    }
}
