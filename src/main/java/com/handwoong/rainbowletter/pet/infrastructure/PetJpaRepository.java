package com.handwoong.rainbowletter.pet.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {
}
