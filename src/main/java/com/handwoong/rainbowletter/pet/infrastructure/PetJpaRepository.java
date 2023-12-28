package com.handwoong.rainbowletter.pet.infrastructure;

import com.handwoong.rainbowletter.pet.domain.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {
    @Query("SELECT p FROM PetEntity p JOIN FETCH p.personality JOIN FETCH p.favoriteEntity WHERE p.memberEntity.email = :email AND p.id = :petId")
    Optional<Pet> findOneById(final String email, final Long petId);

    @Query("SELECT p FROM PetEntity p JOIN FETCH p.personality JOIN FETCH p.favoriteEntity WHERE p.memberEntity.email = :email")
    List<Pet> findAll(final String email);

    @Query("SELECT p FROM PetEntity p JOIN FETCH p.imageEntity WHERE p.memberEntity.email = :email AND p.id = :petId")
    Optional<Pet> findOneByIdWithImage(final String email, final Long petId);
}
