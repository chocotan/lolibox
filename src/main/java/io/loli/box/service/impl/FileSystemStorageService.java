package io.loli.box.service.impl;

import io.loli.box.service.StorageService;
import io.loli.box.startup.LoliBoxConfig;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemStorageService implements StorageService {

    @Override
    public URL upload(File file) throws IOException {
        Path targetPath = new File(LoliBoxConfig.getInstance().getSavePath(), file.getName()).toPath();
        Files.copy(file.toPath(), targetPath);

        return targetPath.toUri().toURL();
    }
}
