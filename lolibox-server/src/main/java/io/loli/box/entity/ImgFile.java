package io.loli.box.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author choco
 */
@Entity
public class ImgFile {

    @Id
    private Long id;
    private Date createDate;

    @Column(name = "deleted")
    private boolean delete;
    private String originName;
    private String shortName;

    private long size;

    @ManyToOne
    private ImgFolder folder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ImgFolder getFolder() {
        return folder;
    }

    public void setFolder(ImgFolder folder) {
        this.folder = folder;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
