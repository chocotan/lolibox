package io.loli.box.service;

import io.loli.box.dao.ImgFileRepository;
import io.loli.box.dao.ImgFolderRepository;
import io.loli.box.entity.ImgFolder;
import io.loli.box.entity.ImgFolderPk;
import io.loli.box.util.FileBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractStorageService implements StorageService {
    @Autowired
    private ImgFolderRepository imgFolderRepository;
    @Autowired
    protected ImgFileRepository imgFileRepository;

    public List<FileBean> getYears() {
        return imgFolderRepository.findAll().stream().map(f -> f.getId().getYear())
                .collect(Collectors.toSet()).stream().map(str -> {
                    FileBean fb = new FileBean();
                    fb.setFile(false);
                    fb.setName(String.valueOf(str));
                    fb.setUrl(String.valueOf(str));
                    return fb;
                }).collect(Collectors.toList());
    }


    public List<FileBean> getMonthsByYear(String year) {
        return imgFolderRepository.findByYear(Integer.parseInt(year)).stream()
                .map(f -> f.getId().getMonth()).collect(Collectors.toSet()).stream()
                .map(m -> {
                            FileBean fb = new FileBean();
                            fb.setFile(false);
                            fb.setName(String.valueOf(m));
                            fb.setUrl(String.valueOf(m));
                            return fb;
                        }
                ).collect(Collectors.toList());
    }


    public List<FileBean> getDaysByMonth(String year, String month) {
        return imgFolderRepository.findByYearAndMonth(Integer.parseInt(year), Integer.parseInt(month)).stream().map(m -> {
                    FileBean fb = new FileBean();
                    fb.setFile(false);
                    fb.setName(String.valueOf(m.getId().getDay()));
                    fb.setUrl(String.valueOf(m.getId().getDay()));
                    return fb;
                }
        ).collect(Collectors.toList());
    }

    public List<FileBean> getFilesByDay(String year, String month, String day) {
        if (year == null) {
            return this.getYears();
        }
        if (month == null) {
            return this.getMonthsByYear(year);
        }
        if (day == null) {
            return this.getDaysByMonth(year, month);
        }

        ImgFolderPk pk = new ImgFolderPk();
        pk.setDay(Integer.parseInt(day));
        pk.setMonth(Integer.parseInt(month));
        pk.setYear(Integer.parseInt(year));
        ImgFolder folder = imgFolderRepository.findOne(pk);
        return folder.getFiles().stream().map(f -> {
            FileBean fb = new FileBean();
            fb.setFile(true);
            fb.setName(f.getOriginName());
            fb.setUrl(f.getShortName());
            fb.setLastModified(f.getCreateDate());
            fb.setSize(f.getSize());
            return fb;
        }).collect(Collectors.toList());
    }

    public void deleteFile(String name) {
        imgFileRepository.updateDeleteByShortName(name, true);
    }
}