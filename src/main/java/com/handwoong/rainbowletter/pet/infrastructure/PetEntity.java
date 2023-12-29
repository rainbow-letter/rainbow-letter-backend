package com.handwoong.rainbowletter.pet.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.favorite.infrastructure.FavoriteEntity;
import com.handwoong.rainbowletter.image.infrastructure.ImageEntity;
import com.handwoong.rainbowletter.member.infrastructure.MemberEntity;
import com.handwoong.rainbowletter.pet.domain.Pet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;

@Getter
@Entity
@Table(name = "pet")
public class PetEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String species;

    @NotNull
    private String owner;

    private LocalDate deathAnniversary;

    @ElementCollection
    @JoinTable(name = "pet_personality")
    private Set<String> personality = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity memberEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "favorite_id", referencedColumnName = "id")
    private FavoriteEntity favoriteEntity;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity imageEntity;

    public Pet toModel() {
        return Pet.builder()
                .id(id)
                .name(name)
                .species(species)
                .owner(owner)
                .deathAnniversary(deathAnniversary)
                .personality(personality)
                .member(memberEntity.toModel())
                .favorite(favoriteEntity.toModel())
                .image(Objects.nonNull(imageEntity) ? imageEntity.toModel() : null)
                .build();
    }

    public static PetEntity fromModel(final Pet pet) {
        final PetEntity petEntity = new PetEntity();
        petEntity.id = pet.id();
        petEntity.name = pet.name();
        petEntity.species = pet.species();
        petEntity.owner = pet.owner();
        petEntity.deathAnniversary = pet.deathAnniversary();
        petEntity.personality = pet.personality();
        petEntity.memberEntity = MemberEntity.from(pet.member());
        petEntity.favoriteEntity = FavoriteEntity.from(pet.favorite());
        petEntity.imageEntity = Objects.nonNull(pet.image()) ? ImageEntity.from(pet.image()) : null;
        return petEntity;
    }
}
