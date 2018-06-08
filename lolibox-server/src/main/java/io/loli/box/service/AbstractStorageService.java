package io.loli.box.service;

import io.loli.box.dao.ImgFileRepository;
import io.loli.box.entity.ImgFile;
import io.loli.box.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractStorageService implements StorageService {
    @Autowired
    protected ImgFileRepository imgFileRepository;

    public void deleteFile(String name) {
        imgFileRepository.updateDeleteByShortName(name, true);
    }

    @Override
    public boolean deleteFile(String name, User user) {
        ImgFile file = imgFileRepository.findByShortName(name);
        if(file.getUser().getId().equals(user.getId())) {
            return true;
        } else {
            return false;
        }
    }
}