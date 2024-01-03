package com.handwoong.rainbowletter.pet.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.favorite.infrastructure.FavoriteEntity;
import com.handwoong.rainbowletter.image.infrastructure.ImageEntity;
import com.handwoong.rainbowletter.member.infrastructure.MemberEntity;
import com.handwoong.rainbowletter.pet.domain.Pet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import org.hibernate.Hibernate;

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
    @JoinTable(name = "pet_personality", joinColumns = {@JoinColumn(name = "pet_id", referencedColumnName = "id")})
    @Column(name = "personality")
    private Set<String> personalities = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity memberEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "favorite_id", referencedColumnName = "id")
    private FavoriteEntity favoriteEntity;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity imageEntity;

    public static PetEntity from(final Pet pet) {
        final PetEntity petEntity = new PetEntity();
        petEntity.id = pet.id();
        petEntity.name = pet.name();
        petEntity.species = pet.species();
        petEntity.owner = pet.owner();
        petEntity.deathAnniversary = pet.deathAnniversary();
        petEntity.personalities = pet.personalities();
        petEntity.memberEntity = Objects.nonNull(pet.member()) ? MemberEntity.from(pet.member()) : null;
        petEntity.favoriteEntity = FavoriteEntity.from(pet.favorite());
        petEntity.imageEntity = Objects.nonNull(pet.image()) ? ImageEntity.from(pet.image()) : null;
        return petEntity;
    }

    public Pet toModel() {
        return Pet.builder()
                .id(id)
                .name(name)
                .species(species)
                .owner(owner)
                .deathAnniversary(deathAnniversary)
                .personalities(personalities)
                .member(memberEntity.toModel())
                .favorite(favoriteEntity.toModel())
                .image(Objects.nonNull(imageEntity) ? imageEntity.toModel() : null)
                .build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (Objects.isNull(obj) || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        PetEntity entity = (PetEntity) obj;
        return Objects.nonNull(id) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? id.intValue() : 0;
    }
}
