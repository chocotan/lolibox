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

    // 0-initial 1-upload  2-upload error  3-porn  4-check error  5=normal
    private Integer greenStatus = 0;
    private Float greenPoint;
    private String greenTaskId;

    private long size;

    @ManyToOne
    private User user;

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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Integer getGreenStatus() {
        return greenStatus;
    }

    public void setGreenStatus(Integer greenStatus) {
        this.greenStatus = greenStatus;
    }


    public Float getGreenPoint() {
        return greenPoint;
    }

    public void setGreenPoint(Float greenPoint) {
        this.greenPoint = greenPoint;
    }


    public String getGreenTaskId() {
        return greenTaskId;
    }

    public void setGreenTaskId(String greenTaskId) {
        this.greenTaskId = greenTaskId;
    }
}
