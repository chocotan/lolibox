package io.loli.box.service.impl;

import io.loli.box.service.AbstractStorageService;
import io.loli.box.startup.LoliBoxConfig;
import io.loli.box.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSystemStorageService extends AbstractStorageService {
    private static List<SuffixBean> suffixes = new ArrayList<>();

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

    @Override
    public URL upload(InputStream is, String filename) throws IOException {
        String suffix = FileUtil.getSuffix(filename);
        if (!suffixes.contains(new SuffixBean(suffix))) {
            throw new IllegalArgumentException("File you uploaded is not an image.");
        }
        Path targetPath = new File(LoliBoxConfig.getInstance().getSavePath(), FileUtil.getFileName()
            + (suffix.equals("") ? "" : "." + suffix)).toPath();
        Files.copy(is, targetPath);
        return targetPath.toUri().toURL();
    }

    final static class SuffixBean {
        private String suffix;

        public SuffixBean(String suffix) {
            this.suffix = suffix;
        }

        public String toString() {
            return suffix.toLowerCase();
        }

        public int hashCode() {
            return suffix.toLowerCase().hashCode();
        }

        public boolean equals(Object target) {
            if (target != null && target instanceof SuffixBean) {
                SuffixBean suf = (SuffixBean) target;
                if (this.suffix.equalsIgnoreCase(suf.suffix)) {
                    return true;
                } else {
                    if (this.suffix.equalsIgnoreCase("jpg") && suf.suffix.equalsIgnoreCase("jpeg")) {
                        return true;
                    }

                    if (this.suffix.equalsIgnoreCase("jpeg") && suf.suffix.equalsIgnoreCase("jpg")) {
                        return true;
                    }

                    return false;
                }
            } else {
                return false;
            }

        }
    }
}