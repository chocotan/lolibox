package io.loli.box.service.impl;

import io.loli.box.service.AbstractStorageService;
import io.loli.box.util.FileBean;
import io.loli.box.util.FileUtil;
import io.loli.box.util.SuffixBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class FileSystemStorageService extends AbstractStorageService {
    private static List<SuffixBean> suffixes = new ArrayList<SuffixBean>();

    static {
        suffixes.add(new SuffixBean("jpg"));
        suffixes.add(new SuffixBean("jpeg"));
        suffixes.add(new SuffixBean("gif"));
        suffixes.add(new SuffixBean("png"));
        suffixes.add(new SuffixBean("bmp"));
        suffixes.add(new SuffixBean("JPG"));
        suffixes.add(new SuffixBean("JPEG"));
        suffixes.add(new SuffixBean("GIF"));
        suffixes.add(new SuffixBean("PNG"));
        suffixes.add(new SuffixBean("BMP"));
    }

    @Value(value = "${storage.filesystem.imgFolder}")
    private String path;

    @Override
    public String upload(InputStream is, String filename, String contentType, long length) throws IOException {
        String suffix = FileUtil.getSuffix(filename);
        if (!suffixes.contains(new SuffixBean(suffix))) {
            throw new IllegalArgumentException("File you uploaded is not an image.");
        }
        String savePath = path;
        String saveDate = getCurrentSaveDate();
        String name = FileUtil.getFileName() + (suffix.equals("") ? "" : "." + suffix);
        Path targetPath = new File(savePath + File.separator + saveDate, name).toPath();
        Files.copy(is, targetPath);
        return saveDate + "/" + name;
    }

    /**
     * Get folder for today and will create it if not exist
     *
     * @return generated date folder
     */
    public String getCurrentSaveDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        // I don't known why calendar start with 0
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        String dayStr = String.valueOf(day);
        if (day < 10) {
            dayStr = "0" + dayStr;
        }
        String path = year + File.separator + monthStr + File.separator
                + dayStr;

        String savePath = this.path;
        if (savePath.endsWith(File.separator)) {
        } else {
            savePath += File.separator;
        }
        savePath += path;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }

    public List<FileBean> getYears() {
        return fileToBean(Arrays.asList(this.getRootFile().listFiles()));
    }

    public List<FileBean> getMonthsByYear(String year) {
        String rootFolderString = this.getRootFileString();
        String yearFolderString = rootFolderString + File.separator + year;
        File file = new File(yearFolderString);
        return fileToBean(Arrays.asList(file.listFiles()));
    }

    private List<FileBean> fileToBean(List<File> files) {
        return files.stream().map(file -> {
            FileBean bean = new FileBean();
            bean.setFile(true);
            bean.setLastModified(new Date(file.lastModified()));
            bean.setName(file.getName());
            bean.setSize(file.length());
            return bean;
        }).collect(Collectors.toList());
    }

    public List<FileBean> getDaysByMonth(String year, String month) {
        String rootFolderString = this.getRootFileString();
        String yearFolderString = rootFolderString + File.separator + year;
        String monthFolderString = yearFolderString + File.separator + month;
        File file = new File(monthFolderString);
        return fileToBean(Arrays.asList(file.listFiles()));
    }

    public List<FileBean> getFilesByDay(String year, String month, String day) {
        if (StringUtils.isBlank(year)) {
            return this.getYears();
        }
        if (StringUtils.isNotBlank(year) && StringUtils.isBlank(month)) {
            return this.getMonthsByYear(year);
        }
        if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isBlank(day)) {
            return this.getDaysByMonth(year, month);
        }

        if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day)) {
            String rootFolderString = this.getRootFileString();
            String yearFolderString = rootFolderString + File.separator + year;
            String monthFolderString = yearFolderString + File.separator + month;
            String dayFolderString = monthFolderString + File.separator + day;
            File file = new File(dayFolderString);
            return fileToBean(Arrays.asList(file.listFiles()));
        }

        throw new IllegalArgumentException("Parameter is not illegal:" + "year=" + year + ", month=" + month + ", day="
                + day);

    }

    private File getRootFile() {
        return new File(path);
    }

    private String getRootFileString() {
        return new File(path).getPath();
    }

    public void deleteFile(String year, String month, String day, String name) {
        String path = this.path
                + File.separator + year + File.separator + month + File.separator + day
                + File.separator + name;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}