package com.handwoong.rainbowletter.domain.image.controller;

import com.handwoong.rainbowletter.domain.image.dto.ImageUploadResponse;
import com.handwoong.rainbowletter.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponse> upload(@RequestPart final MultipartFile file,
                                                      @RequestParam final String type) {
        final ImageUploadResponse response = imageService.upload(file, type);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
