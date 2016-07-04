package io.loli.box.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author choco
 */
@Entity
@Deprecated
public class ImgFolder {
    @EmbeddedId
    private ImgFolderPk id;
    private int imgCount;

    @OneToMany(mappedBy = "folder")
    private List<ImgFile> files;

    public ImgFolderPk getId() {
        return id;
    }

    public void setId(ImgFolderPk id) {
        this.id = id;
    }


    public List<ImgFile> getFiles() {
        return files;
    }

    public void setFiles(List<ImgFile> files) {
        this.files = files;
    }

    public int getImgCount() {
        return imgCount;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }
}
