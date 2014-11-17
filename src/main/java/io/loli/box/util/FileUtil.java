package io.loli.box.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    /*
     * Upload a file to tmp dir waiting for upload. <br> File name is
     */
    public static void uploadToTemp(File file) throws FileNotFoundException, IOException {
        Path target;
        target = new File(System.getProperty("java.io.tmpdir"), getFileName()).toPath();
        Files.copy(new BufferedInputStream(new FileInputStream(file)), target);
    }

    public synchronized static String getFileName() {
        return String.valueOf(System.nanoTime());
    }

    /**
     * Get file suffix by its name<br>
     * Example:
     * 
     * <pre>
     * "test.jpg" =&gt; "jpg"
     * "test" =&gt; ""
     * </pre>
     * 
     * @param filename name of file
     * @return Suffix of this file
     */
    public static String getSuffix(String filename) {
        if (filename.indexOf(".") >= 0) {
            return filename.substring(filename.indexOf(".") + 1);
        } else {
            return "";
        }
    }
}
