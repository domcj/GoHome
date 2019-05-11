package com.huarun.gohome.model;

import java.io.Serializable;
import java.util.List;

public class Missingperson implements Serializable {
    private Integer id;

    private String title;

    private String category;

    private String poston;

    private String detail;

    private String image;

    private Boolean isaddface;

    private Integer uId;

    private List<ClueInfo> clueInfos;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getPoston() {
        return poston;
    }

    public void setPoston(String poston) {
        this.poston = poston == null ? null : poston.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Boolean getIsaddface() {
        return isaddface;
    }

    public void setIsaddface(Boolean isaddface) {
        this.isaddface = isaddface;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public List<ClueInfo> getClueInfos() {
        return clueInfos;
    }

    public void setClueInfos(List<ClueInfo> clueInfos) {
        this.clueInfos = clueInfos;
    }
}