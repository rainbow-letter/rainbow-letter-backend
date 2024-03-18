package com.handwoong.rainbowletter.data.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaRepository extends JpaRepository<DataEntity, Long> {
}
