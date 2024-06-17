package com.handwoong.rainbowletter.common.infrastructure;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractFileManager {

    protected void createBaseDir(final String basePath, final String dirName) {
        final File baseDir = createAbsolutePath(basePath, dirName).toFile();
        if (!baseDir.exists() && baseDir.mkdirs()) {
            log.info("폴더 {}를 생성하였습니다.", dirName);
        }
    }

    protected Path createAbsolutePath(final String basePath, final String... paths) {
        return Paths.get(basePath, paths).toAbsolutePath();
    }

    protected File[] searchFiles(final Path searchDirPath, final String fileName) {
        final File searchTargetDir = searchDirPath.toFile();
        return searchTargetDir.listFiles(file -> file.isFile() && file.getName().startsWith(fileName));
    }

    protected File getFirstFile(final File[] files) {
        if (Objects.isNull(files) || files.length == 0) {
            throw new IllegalArgumentException("파일을 찾지 못했습니다.");
        }
        return files[0];
    }

}
