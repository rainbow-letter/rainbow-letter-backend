package com.handwoong.rainbowletter.pet.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {
    @Query("SELECT p FROM PetEntity p JOIN FETCH p.personalities JOIN FETCH p.favoriteEntity WHERE p.memberEntity.email = :email AND p.id = :petId")
    Optional<PetEntity> findOneById(@Param("email") final String email, @Param("petId") final Long petId);

    @Query("SELECT DISTINCT p FROM PetEntity p LEFT OUTER JOIN FETCH p.personalities JOIN FETCH p.favoriteEntity WHERE p.memberEntity.email = :email")
    List<PetEntity> findAll(@Param("email") final String email);

    @Query("SELECT p FROM PetEntity p JOIN FETCH p.imageEntity WHERE p.memberEntity.email = :email AND p.id = :petId")
    Optional<PetEntity> findOneByIdWithImage(@Param("email") final String email, @Param("petId") final Long petId);
}
