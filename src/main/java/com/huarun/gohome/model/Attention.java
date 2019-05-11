package com.huarun.gohome.model;

import java.io.Serializable;

public class Attention implements Serializable {
    private Integer uId;

    private Integer mpId;

    private static final long serialVersionUID = 1L;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getMpId() {
        return mpId;
    }

    public void setMpId(Integer mpId) {
        this.mpId = mpId;
    }
}