package com.handwoong.rainbowletter.image.infrastructure;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.handwoong.rainbowletter.common.infrastructure.AbstractFileManager;
import com.handwoong.rainbowletter.common.service.port.UuidGenerator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageFileManager extends AbstractFileManager {

    private static final String DEFAULT_IMAGE_DIR_NAME = "images";

    private final UuidGenerator uuidHolder;

    public String save(final MultipartFile file) {
        final String fileName = createFileName(file);
        final String dirName = createDirName();
        final Path filePath = createAbsolutePath(DEFAULT_IMAGE_DIR_NAME, dirName, fileName);

        try (final InputStream inputStream = file.getInputStream()) {
            createBaseDir(DEFAULT_IMAGE_DIR_NAME, dirName);
            Files.copy(inputStream, filePath);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("파일 저장에 실패하였습니다.", exception);
        }
        return "%s/%s".formatted(dirName, fileName);
    }

    private String createFileName(final MultipartFile file) {
        final String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return uuidHolder.generate() + "." + extension;
    }

    private String createDirName() {
        final LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public Path load(final String dirName, final String fileName) {
        final Path searchDirPath = createAbsolutePath(DEFAULT_IMAGE_DIR_NAME, dirName);
        final File[] files = searchFiles(searchDirPath, fileName);
        return getFirstFile(files).toPath();
    }

    public void delete(final String dirName, final String fileName) throws IOException {
        final Path searchDirPath = createAbsolutePath(DEFAULT_IMAGE_DIR_NAME, dirName);
        final File[] files = searchFiles(searchDirPath, fileName);
        final Path filePath = getFirstFile(files).toPath();
        Files.delete(filePath);
    }

}
