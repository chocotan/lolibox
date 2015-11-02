package io.loli.box.util;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class FileBean implements Serializable {

    public static final long serialVersionUID = -2126687016928386103L;

    private long size;
    private String name;
    private Date lastModified;
    private boolean file;

    public FileBean() {
    }

    public FileBean(File file) {
        setName(file.getName());
        setLastModified(new Date(file.lastModified()));
        setFile(file.isFile());
        if (this.isFile()) {
            this.setSize(file.length());
        } else {
            this.setSize(0L);
        }
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }
}
