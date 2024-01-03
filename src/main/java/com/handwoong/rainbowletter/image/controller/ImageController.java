package com.handwoong.rainbowletter.image.controller;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.IMAGE_EMPTY;

import com.handwoong.rainbowletter.image.controller.port.ImageService;
import com.handwoong.rainbowletter.image.controller.response.ImageUploadResponse;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<ImageUploadResponse> upload(
            @RequestPart @Valid @NotBlank(message = IMAGE_EMPTY) final MultipartFile file,
            @RequestParam @Valid @NotNull final ImageType type) {
        final Image image = imageService.upload(file, type);
        final ImageUploadResponse response = ImageUploadResponse.from(image);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
