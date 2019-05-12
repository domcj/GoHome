package com.huarun.gohome.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.huarun.gohome.bean.DetectResponse;
import com.huarun.gohome.bean.FaceDetailResponse;
import com.huarun.gohome.bean.FacePPApi;
import com.huarun.gohome.bean.FaceSet;
import com.huarun.gohome.bean.FaceSetAddResponse;
import com.huarun.gohome.bean.FaceSetCreatResponse;
import com.huarun.gohome.bean.FaceSetDetailResponse;
import com.huarun.gohome.bean.FaceSetListResponse;
import com.huarun.gohome.bean.FaceSetRemoveResponse;
import com.huarun.gohome.bean.FaceToken;
import com.huarun.gohome.bean.SearchFaceResult;
import com.huarun.gohome.bean.SearchResponse;
import com.huarun.gohome.service.FacePPservice;

import sun.misc.BASE64Encoder;

@Service
public class FaceppServiceImpl implements FacePPservice {

    private static Logger logger = LoggerFactory.getLogger(FaceppServiceImpl.class);
    private FacePPApi facePPApi = new FacePPApi();

    private static final List<String> suffixes = Arrays.asList("image/png","image/jpeg","image/jpg");
    @Value("${facepp.confidence}")
    private float confidence;
    @Value("${facepp.faceSetName}")
    private String faceSetName;

    @Override
    public FaceSetCreatResponse createFaceSet(String faceSetName) {
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("outer_id", faceSetName);
        String result = facePPApi.facesetCreate(paraMap);
        FaceSetCreatResponse response = JSON.parseObject(result, FaceSetCreatResponse.class);
        return response;
    }

    @Override
    public FaceSetListResponse getFaceSets() {
        String result = facePPApi.facesetList(null);
        FaceSetListResponse response = JSON.parseObject(result, FaceSetListResponse.class);
        for (FaceSet faceSet : response.getFaceSets()) {
            System.out.println(faceSet.getOuter_id() + "_" + faceSet.getFaceset_token() + "_" + faceSet.getUser_data());
        }
        return response;
    }

    @Override
    public String getFaceToken(byte[] fileByte) {
        HashMap<String, String> paraMap = new HashMap<>();
        BASE64Encoder encoder = new BASE64Encoder();
        paraMap.put("image_base64", encoder.encode(fileByte));
        String result = facePPApi.detect(paraMap);
        if (result==null) {
            return null;
        }
        DetectResponse response = JSON.parseObject(result, DetectResponse.class);
        if (StringUtils.isNotEmpty(response.getError_message())) {
            return null;
        }
        switch (response.getFaces().size()) {
            case 0:
                return null;
            case 1:
                return response.getFaces().get(0).getFace_token();
            default:
                return "multi-faces";
        }
    }

    @Override
    public boolean addUserId(String faceToken, String userId) {
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("face_token", faceToken);
        paraMap.put("user_id", userId);
        String result = facePPApi.setFaceUserId(paraMap);
        if (result==null) {
            return false;
        }
        FaceToken response = JSON.parseObject(result, FaceToken.class);
        return response.getUser_id()!=null ? true : false;
    }

    @Override
    public boolean addFaceToken(String faceToken, String outerID) {
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("outer_id", outerID);
        paraMap.put("face_tokens", faceToken);
        String result = facePPApi.facesetAddFace(paraMap);
        if (result==null) {
            return false;
        }
        FaceSetAddResponse response = JSON.parseObject(result, FaceSetAddResponse.class);
        return response.getFace_added() == 1 ? true : false;
    }

    @Override
    public FaceSetRemoveResponse deleteFaces(String outerID, String face_tokens) {
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("outer_id", outerID);
        paraMap.put("face_tokens", face_tokens);
        String result = facePPApi.facesetRemoveFace(paraMap);
        if (result==null) {
            return null;
        }
        FaceSetRemoveResponse response = JSON.parseObject(result, FaceSetRemoveResponse.class);
        return response;
    }

    @Override
    public String getFaceInfo(String faceToken) {
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("face_token", faceToken);
        String result = facePPApi.faceDetail(paraMap);
        if (result==null) {
            return null;
        }
        FaceDetailResponse response = JSON.parseObject(result, FaceDetailResponse.class);
        return response.toString();
    }

    @Override
    public String getFaceSetInfo(String outerID) {
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("outer_id", outerID);
        String result = facePPApi.facesetDetail(paraMap);
        if (result==null) {
            return null;
        }
        FaceSetDetailResponse response = JSON.parseObject(result, FaceSetDetailResponse.class);
        return response.toString();
    }

    @Override
    public String addFace(String outerId, byte[] buff, String userId) {
        String faceToken = null;
        int retryTimes = 0;
        do {
            faceToken = getFaceToken(buff);
        } while (faceToken==null&&++retryTimes<5);

        if (faceToken==null) {
            return "未识别到有效人脸";
        }
        if ("multi-faces".equals(faceToken)) {
            return "获取token异常->multi-faces";
        }
        retryTimes = 0;
        boolean isSuccess = false;
        do {
            isSuccess = addUserId(faceToken, userId);
        } while (!isSuccess&&++retryTimes<5);

        if (!isSuccess) {
            return "添加userId异常";
        }
        retryTimes = 0;
        isSuccess = false;
        do {
            isSuccess = addFaceToken(faceToken, outerId);
        } while (!isSuccess&&++retryTimes<5);

        if (!isSuccess) {
            return "face++照片集添加异常";
        }
        return "人脸添加成功";
    }

    @Override
    public Map<String, String> faceSearch(byte[] bytes, String outerId) {
        Map<String, String> resultMap = new HashMap<>();
        String faceToken = this.getFaceToken(bytes);
        if (faceToken==null) {
            faceToken = this.getFaceToken(bytes);
        }
        if (faceToken==null) {
            resultMap.put("error", "人脸检测异常");
            return resultMap;
        }
        if ("multi-faces".equals(faceToken)) {
            resultMap.put("error", "只支持单个人脸检测");
            return resultMap;
        }
        List<SearchFaceResult> results = this.searchFaceByFaceToken(faceToken, outerId);
        if (CollectionUtils.isEmpty(results)) {
            resultMap.put("error", "未搜索到相似人脸");
            return resultMap;
        }
        if (results.get(0).getConfidence()<confidence) {
            resultMap.put("error", "未搜索到相似人脸");
            return resultMap;
        }
        resultMap.put("userId", results.get(0).getUser_id());
        resultMap.put("confidence", results.get(0).getConfidence()+"");
        return resultMap;
    }

    private List<SearchFaceResult> searchFaceByFaceToken(String faceToken, String outerID){
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("outer_id", outerID);
        paraMap.put("face_token", faceToken);
        String result = facePPApi.search(paraMap);
        if (result==null) {
            return null;
        }
        SearchResponse response = JSON.parseObject(result, SearchResponse.class);
        return response.getResults();
    }

    /**
     * 执行函数
     */
    public static void main(String[] args) {

    }

}
