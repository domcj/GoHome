package com.huarun.gohome.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huarun.gohome.bean.FaceSetCreatResponse;
import com.huarun.gohome.bean.FaceSetListResponse;
import com.huarun.gohome.bean.FaceSetRemoveResponse;

@Service
	public interface FacePPservice {

		/**
	 * 创建FaceSet
	 */
	public FaceSetCreatResponse createFaceSet(String faceSetName);

	/**
	 *   由自定义FaceSet名称获取FaceSet的详细信息
	 */
	public FaceSetListResponse getFaceSets();

	/**
	 * 获取一张照片的face_token，返回Json对应DetectGroup
	 */
	public String getFaceToken(byte[] fileByte);

	/**
	 * 为指定face_token添加user_id
	 */
	public boolean addUserId(String faceToken, String userId);

	/**
	 * 向指定FaceSet中添加Face_token，返回Json对应AddFaceTokenGroup
	 */
	public boolean addFaceToken(String faceToken, String outerID);

	/**
	 * 删除FaceSet中所有Face
	 * face_tokens字符串传入RemoveAllFaceTokens会移除FaceSet内所有的face_token
	 */
	public FaceSetRemoveResponse deleteFaces(String outerID, String face_tokens);

	/**
	 * 根据token获取信息
	 */
	public String getFaceInfo(String faceToken);

	/**
	 * 获取faceset中所有faceToken信息
	 */
	public String getFaceSetInfo(String outerID);

	/**
	 * FaceSet中添加人脸
	 */
	public String addFace(String outerId, byte[] buff, String userId);

	public Map<String, String> faceSearch(byte[] file);
}
