package com.handwoong.rainbowletter.image.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.handwoong.rainbowletter.image.controller.port.ImageService;
import com.handwoong.rainbowletter.image.controller.response.ImageLoadResponse;
import com.handwoong.rainbowletter.image.domain.Image;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.infrastructure.ImageFileManager;
import com.handwoong.rainbowletter.image.service.port.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageFileManager imageFileManager;

    @Override
    public ImageLoadResponse load(final String dirName, final String fileName) throws IOException {
        final Path path = imageFileManager.load(dirName, fileName);
        final Resource resource = new FileSystemResource(path);
        return ImageLoadResponse.of(MediaType.parseMediaType(Files.probeContentType(path)), resource);
    }

    @Override
    @Transactional
    public Image upload(final MultipartFile file, final ImageType type) {
        final String objectKey = imageFileManager.save(file);
        final Image image = Image.create(type, objectKey, "", "");
        return imageRepository.save(image);
    }
}
