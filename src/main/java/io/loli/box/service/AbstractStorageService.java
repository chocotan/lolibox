package io.loli.box.service;

import io.loli.box.service.impl.FileSystemStorageService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class AbstractStorageService implements StorageService {

    public URL upload(File file) throws IOException {
        return upload(new BufferedInputStream(new FileInputStream(file)), file.getName());
    }

    @Override
    public abstract URL upload(InputStream is, String filename) throws IOException;

    public static StorageService getDefaultInstance() {
        return new FileSystemStorageService();
    }
}