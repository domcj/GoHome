package com.huarun.gohome.model;

import java.io.Serializable;

public class ClueInfo implements Serializable {
    private Integer id;

    private Integer mpId;

    private Integer uId;

    private String loseName;

    private String loseIphone;

    private String loseSex;

    private String loseAddress;

    private String description;

    private String status;

    private String imageurl;

    private String confidence;

    private Missingperson  missingperson;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMpId() {
        return mpId;
    }

    public void setMpId(Integer mpId) {
        this.mpId = mpId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getLoseName() {
        return loseName;
    }

    public void setLoseName(String loseName) {
        this.loseName = loseName == null ? null : loseName.trim();
    }

    public String getLoseIphone() {
        return loseIphone;
    }

    public void setLoseIphone(String loseIphone) {
        this.loseIphone = loseIphone == null ? null : loseIphone.trim();
    }

    public String getLoseSex() {
        return loseSex;
    }

    public void setLoseSex(String loseSex) {
        this.loseSex = loseSex == null ? null : loseSex.trim();
    }

    public String getLoseAddress() {
        return loseAddress;
    }

    public void setLoseAddress(String loseAddress) {
        this.loseAddress = loseAddress == null ? null : loseAddress.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence == null ? null : confidence.trim();
    }

    public Missingperson getMissingperson() {
        return missingperson;
    }

    public void setMissingperson(Missingperson missingperson) {
        this.missingperson = missingperson;
    }
}