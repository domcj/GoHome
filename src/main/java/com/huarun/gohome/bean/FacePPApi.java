package com.huarun.gohome.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.huarun.gohome.service.FacePPservice;
import com.huarun.gohome.util.HttpUtils;

import groovy.lang.Singleton;

public class FacePPApi {

	String BASE_URL = "https://api-cn.faceplusplus.com/facepp";
	String API_DETECT = BASE_URL + "/v3/detect";
	String API_SEARCH = BASE_URL + "/v3/search";
	String API_FACE_SET_CREATE = BASE_URL + "/v3/faceset/create";
	String API_FACE_SET_ADD_FACE = BASE_URL + "/v3/faceset/addface";
	String API_FACE_SET_REMOVE_FACE = BASE_URL + "/v3/faceset/removeface";
	String API_FACE_SET_GET_FACESETS = BASE_URL + "/v3/faceset/getfacesets";
	String API_FACE_SET_GET_DETAIL = BASE_URL + "/v3/faceset/getdetail";
	String API_FACE_GET_DETAIL = BASE_URL + "/v3/face/getdetail";
	String API_FACE_SET_USER_ID = BASE_URL + "/v3/face/setuserid";

	public String detect(Map<String, String> params) {
		return detect(params, null);
	}

	public String detect(Map<String, String> params, byte[] filePath) {
		return HttpUtils.post(API_DETECT, buildParams(params), filePath);
	}

	public String search(Map<String, String> params) {
		return search(params, null);
	}

	public String search(Map<String, String> params, byte[] filePath) {
		return HttpUtils.post(API_SEARCH, buildParams(params), filePath);
	}

	public String facesetCreate(Map<String, String> params) {
		return HttpUtils.post(API_FACE_SET_CREATE, buildParams(params));
	}

	public String facesetAddFace(Map<String, String> params) {
		return HttpUtils.post(API_FACE_SET_ADD_FACE, buildParams(params));
	}

	public String facesetRemoveFace(Map<String, String> params) {
		return HttpUtils.post(API_FACE_SET_REMOVE_FACE, buildParams(params));
	}

	public String facesetDetail(Map<String, String> params) {
		return HttpUtils.post(API_FACE_SET_GET_DETAIL, buildParams(params));
	}

	public String facesetList(Map<String, String> params) {
		return HttpUtils.post(API_FACE_SET_GET_FACESETS, buildParams(params));
	}

	public String faceDetail(Map<String, String> params) {
		return HttpUtils.post(API_FACE_GET_DETAIL, buildParams(params));
	}

	public String setFaceUserId(Map<String, String> params) {
		return HttpUtils.post(API_FACE_SET_USER_ID, buildParams(params));
	}

	private Map<String, String> buildParams(Map<String, String> params) {
		if (params==null) {
			params = new HashMap<>();
		}
		params.put("api_key", "Z9lN8z_iD8exwYxpT1CiLv3s5bZZ0bT-");
		params.put("api_secret", "LUZTTBb5idZn8mC7ED8Pe_dnGpj_Iuu3");
		return params;
	}
}
