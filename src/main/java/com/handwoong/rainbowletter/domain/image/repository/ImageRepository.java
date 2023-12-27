package com.handwoong.rainbowletter.domain.image.repository;

import com.handwoong.rainbowletter.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
