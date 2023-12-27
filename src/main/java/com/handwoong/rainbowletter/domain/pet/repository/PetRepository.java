package com.handwoong.rainbowletter.domain.pet.repository;

import com.handwoong.rainbowletter.domain.pet.dto.PetResponse;
import com.handwoong.rainbowletter.domain.pet.model.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p JOIN FETCH p.personality JOIN FETCH p.favorite WHERE p.member.email = :email AND p.id = :petId")
    Optional<PetResponse> findOne(final String email, final Long petId);

    @Query("SELECT p FROM Pet p JOIN FETCH p.personality JOIN FETCH p.favorite WHERE p.member.email = :email")
    List<PetResponse> findAllMemberPets(final String email);

    @Query("SELECT p FROM Pet p JOIN FETCH p.image WHERE p.member.email = :email AND p.id = :petId")
    Optional<Pet> findPetWithImage(final String email, final Long petId);

    @Query("SELECT p FROM Pet p JOIN FETCH p.personality JOIN FETCH p.favorite WHERE p.member.email = :email AND p.id = :petId")
    Optional<Pet> findOneMemberPet(final String email, final Long petId);
}
