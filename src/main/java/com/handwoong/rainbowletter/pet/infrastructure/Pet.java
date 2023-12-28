package com.handwoong.rainbowletter.pet.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.favorite.infrastructure.Favorite;
import com.handwoong.rainbowletter.image.infrastructure.ImageEntity;
import com.handwoong.rainbowletter.member.infrastructure.MemberEntity;
import com.handwoong.rainbowletter.pet.domain.dto.PetRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String species;

    @NotNull
    private String owner;

    @NotNull
    private LocalDate deathAnniversary;

    @ElementCollection
    private Set<String> personality = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity memberEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "favorite_id", referencedColumnName = "id")
    private Favorite favorite;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity imageEntity;

    private Pet(final String name,
                final String species,
                final String owner,
                final LocalDate deathAnniversary,
                final Set<String> personality,
                final Favorite favorite,
                final MemberEntity memberEntity) {
        this.name = name;
        this.species = species;
        this.owner = owner;
        this.deathAnniversary = deathAnniversary;
        this.personality = personality;
        this.favorite = favorite;
        this.memberEntity = memberEntity;
    }

    public static Pet create(final PetRequest request, final MemberEntity memberEntity) {
        final Favorite favorite = Favorite.create();
        return new Pet(request.name(),
                request.species(),
                request.owner(),
                request.deathAnniversary(),
                request.personality(),
                favorite,
                memberEntity);
    }

    public void changeImage(final ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    public void removeImage() {
        this.imageEntity = null;
    }

    public void update(final PetRequest request) {
        this.name = request.name();
        this.species = request.species();
        this.owner = request.owner();
        this.deathAnniversary = request.deathAnniversary();
        this.personality = request.personality();
    }

    public void disconnect() {
        removeImage();
        this.memberEntity = null;
    }
}
