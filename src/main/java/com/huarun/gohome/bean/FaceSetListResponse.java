package com.huarun.gohome.bean;

import java.util.List;

public class FaceSetListResponse extends BaseResponse {

    private List<FaceSet> faceSets;

    private String next;

    public List<FaceSet> getFaceSets() {
        return faceSets;
    }

    public void setFaceSets(List<FaceSet> faceSets) {
        this.faceSets = faceSets;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "{" +
                "\"faceSets\":" + faceSets +
                ", \"next\":\'" + next + "\'" +
                '}';
    }
}
