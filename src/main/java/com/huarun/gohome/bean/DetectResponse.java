package com.huarun.gohome.bean;

import java.util.List;

public class DetectResponse extends BaseResponse {

    private List<Face> faces; // 人脸列表

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    @Override
    public String toString() {
        return "{" +
                "\"faces\":" + faces +
                '}';
    }
}
