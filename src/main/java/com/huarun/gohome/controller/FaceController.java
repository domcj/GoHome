package com.huarun.gohome.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.huarun.gohome.bean.FaceSetCreatResponse;
import com.huarun.gohome.bean.FaceSetListResponse;
import com.huarun.gohome.model.Missingperson;
import com.huarun.gohome.service.FacePPservice;
import com.huarun.gohome.service.MissingpersonService;
import com.huarun.gohome.util.DownFileUtil;

@Controller
public class FaceController {

	private static Logger logger = LoggerFactory.getLogger(FaceController.class);
	private static ExecutorService service = Executors.newFixedThreadPool(1);

	@Resource
	private FacePPservice facePPservice;
	@Resource
	private MissingpersonService missingpersonService;
	@Value("${facepp.faceSetName}")
	private String faceSetName;

	@RequestMapping("/getAllFace")
	@ResponseBody
	public String getAllFace() {
		String result = null;
		try {
			result = facePPservice.getFaceSetInfo(faceSetName);
		} catch (Exception e) {
			logger.error("error", e);
			return "获取所有face信息异常";
		}
		return result;
	}

	@RequestMapping("/getFaceSets")
	@ResponseBody
	public FaceSetListResponse getFaceSets() {
		FaceSetListResponse result = null;
		try {
			result = facePPservice.getFaceSets();
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
		return result;
	}

	@RequestMapping("/getFace/{faceToken}")
	@ResponseBody
	public String getFace(@PathVariable("faceToken") String faceToken) {
		String result = null;
		try {
			result = facePPservice.getFaceInfo(faceToken);
		} catch (Exception e) {
			logger.error("error", e);
			return "获取face信息异常";
		}
		return result;
	}

	@RequestMapping("/createFaceSet/{faseSet}")
	@ResponseBody
	public FaceSetCreatResponse createFaceSet(@PathVariable String faseSet) {
		FaceSetCreatResponse result = null;
		try {
			result = facePPservice.createFaceSet(faseSet);
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
		return result;
	}

	@RequestMapping("/removeAllFace")
	@ResponseBody
	public FaceSetCreatResponse removeAllFace() {
		FaceSetCreatResponse result = null;
		try {
			result = facePPservice.deleteFaces(faceSetName, "RemoveAllFaceTokens");
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
		return result;
	}

	@RequestMapping("/batchAddFace")
	@ResponseBody
	public String batchAddFace(@RequestParam(required = false) Integer faceNum) {
		if (faceNum==null) {
			faceNum = 10;
		}
		PageHelper.startPage(1, faceNum);
		List<Missingperson> missingpeoples = missingpersonService.selectAddFace();

		AtomicInteger resultNum = new AtomicInteger(0);
		for (Missingperson person : missingpeoples) {
			service.submit(new Runnable() {
				@Override
				public void run() {
					byte[] bytes = DownFileUtil.getImageBytesByUrl(person.getImage());
					if (bytes==null) {
						logger.info(person.getId()+"-->图片下载失败");
						return;
					}
					String result = facePPservice.addFace(faceSetName, bytes, person.getId() + "");
					if ("人脸添加成功".equals(result)) {
						resultNum.getAndIncrement();
						logger.info(person.getId()+"-->"+result);
					} else {
						logger.info(person.getId()+"-->人脸添加失败-->"+result);
					}
					Missingperson missingperson = new Missingperson();
					missingperson.setId(person.getId());
					missingperson.setIsaddface(true);
					missingpersonService.updateByPrimaryKeySelective(missingperson);
				}
			});
		}
		return "添加人脸成功数:"+resultNum;
	}
}
