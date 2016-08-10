package io.loli.box.service;

import io.loli.box.dao.ImgFileRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractStorageService implements StorageService {
    @Autowired
    protected ImgFileRepository imgFileRepository;

    public void deleteFile(String name) {
        imgFileRepository.updateDeleteByShortName(name, true);
    }
}