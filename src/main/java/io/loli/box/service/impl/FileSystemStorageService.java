package io.loli.box.service.impl;

import io.loli.box.service.StorageService;
import io.loli.box.startup.LoliBoxConfig;
import io.loli.box.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemStorageService implements StorageService {

    @Override
    public URL upload(InputStream is, String filename) throws IOException {
        String suffix = FileUtil.getSuffix(filename);
        Path targetPath = new File(LoliBoxConfig.getInstance().getSavePath(), FileUtil.getFileName()
            + (suffix.equals("") ? "" : "." + suffix)).toPath();
        Files.copy(is, targetPath);
        return targetPath.toUri().toURL();
    }

}
