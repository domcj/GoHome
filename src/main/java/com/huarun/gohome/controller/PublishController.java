package com.huarun.gohome.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.huarun.gohome.model.Missingperson;
import com.huarun.gohome.response.BaseResp;
import com.huarun.gohome.service.ClueInfoService;
import com.huarun.gohome.service.FacePPservice;
import com.huarun.gohome.service.MissingpersonService;
import com.huarun.gohome.util.DownFileUtil;

@RestController
public class PublishController {

	private static String imgBasePath = "D:\\picture\\";
	private static String searchBasePath = "D:\\searchPicture\\";
	@Resource
	private MissingpersonService missingpersonService;
	@Resource
	private ClueInfoService clueInfoService;
	@Resource
	private FacePPservice facePPservice;
	@Value("${facepp.faceSetName}")
	private String faceSetName;
	@Value("${image.prefix}")
	private String prefix;

	@RequestMapping("/getPublishs")
	public List<Missingperson> getPublishs(HttpServletRequest request, @RequestParam(required=false) Integer pageIndex, @RequestParam(required=false) Integer pageSize) {
		String userId = request.getHeader("user");
		if (pageIndex==null||pageSize==null) {
			pageIndex = 1;
			pageSize = 10;
		}
		PageHelper.startPage(pageIndex, pageSize);
		List<Missingperson> missingpeoples = missingpersonService.getMissingPersonByUserId(Integer.valueOf(userId));
//		for (Missingperson missingperson : missingpeoples) {
//			List<ClueInfo> clueInfos = clueInfoService.selectByMPId(missingperson.getId());
//			for (ClueInfo clueInfo : clueInfos) {
//				if (StringUtils.isNotEmpty(clueInfo.getImageurl())) {
//					clueInfo.setImageurl(prefix+clueInfo.getImageurl()+"/1");
//				}
//			}
//			missingperson.setClueInfos(clueInfos);
//			missingperson.setImage(prefix+missingperson.getImage()+"/0");
//		}
		return missingpeoples;
	}

	@RequestMapping("/uploadPublish")
	public BaseResp upload(@RequestParam MultipartFile file) {
		BaseResp baseResp = new BaseResp();
		if (file==null||file.getSize()<5) {
			baseResp.setErrMsg("请上传图片");
			return baseResp;
		}
		String fileName = file.getOriginalFilename().split("\\.")[0]+".jpg";
		String result = null;
		String imageUril = DownFileUtil.downImage(file, fileName, "0");
		if (imageUril==null) {
			baseResp.setErrMsg("图片下载失败");
			return baseResp;
		}
		baseResp.setResult(BaseResp.SUCCESS);
		baseResp.setData(prefix+imageUril+"/0");
		return baseResp;
	}

	@RequestMapping("/addPublish")
	public BaseResp addPublish(HttpServletRequest request, @RequestParam String title, @RequestParam String category,@RequestParam String detail,@RequestParam String imgUrl) {
		String imgName = DownFileUtil.getFileName(imgUrl);
		byte[] files = DownFileUtil.loadImage(imgName, "0");
		BaseResp baseResp = new BaseResp();
		String userId = request.getHeader("user");
		String errMsg = null;
		if (StringUtils.isEmpty(title)) {
			baseResp.setErrMsg("请输入标题");
			return baseResp;
		}
		if (StringUtils.isEmpty(category)) {
			baseResp.setErrMsg("请输入性别");
			return baseResp;
		}
		if (StringUtils.isEmpty(detail)) {
			baseResp.setErrMsg("请输入详情");
			return baseResp;
		}
		if (files==null||files.length<5) {
			baseResp.setErrMsg("未获取到有效图片");
			return baseResp;
		}
		Missingperson missingperson = new Missingperson();
		missingperson.setCategory(category);
		missingperson.setTitle(title);
		missingperson.setDetail(detail);
		missingperson.setuId(Integer.valueOf(userId));
		missingperson.setPoston(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		missingperson.setImage(imgName);
		missingpersonService.insert(missingperson);

		Integer maxId = missingperson.getId();
		String result = null;
		try {
			result = facePPservice.addFace(faceSetName, files, maxId+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"人脸添加成功".equals(result)) {
			missingpersonService.deleteByPrimaryKey(maxId);
			baseResp.setErrMsg(result);
			return baseResp;
		}
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;

	}

	@RequestMapping(value = "/getImage/{imgName}/{type}", method = RequestMethod.GET)
	public String IoReadImage(@PathVariable String imgName, @PathVariable String type, HttpServletResponse response) throws IOException {
		ServletOutputStream sos = null;
		FileInputStream ips = null;
		try {
			//获取图片存放路径
			String imgPath = imgBasePath + imgName;
			if ("1".equals(type)) {
				imgPath = searchBasePath + imgName;
			}
			ips = new FileInputStream(new File(imgPath));
			response.setContentType("multipart/form-data");
			sos = response.getOutputStream();
			//读取文件流
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = ips.read(buffer)) != -1){
				sos.write(buffer,0,len);
			}
			sos.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				sos.close();
				ips.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping("/getPublishById/{id}")
	public Missingperson getPublishById(@PathVariable Integer id) {
		Missingperson missingperson = missingpersonService.selectByPrimaryKey(id);
//		List<ClueInfo> clueInfos = clueInfoService.selectByMPId(missingperson.getId());
//		for (ClueInfo clueInfo : clueInfos) {
//			if (StringUtils.isNotEmpty(clueInfo.getImageurl())) {
//				clueInfo.setImageurl(prefix+clueInfo.getImageurl()+"/1");
//			}
//		}
//		missingperson.setClueInfos(clueInfos);
//		missingperson.setImage(prefix+missingperson.getImage()+"/0");
		return missingperson;
	}

	@RequestMapping("/removePublish/{id}")
	public BaseResp removePublish(@PathVariable Integer id) {
		BaseResp baseResp = new BaseResp();
		missingpersonService.deleteByPrimaryKey(id);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}

	@RequestMapping("/updatePublish")
	public BaseResp updatePublish(@RequestParam Integer id,@RequestParam String title,@RequestParam String category,@RequestParam String detail) {
		BaseResp baseResp = new BaseResp();
		Missingperson missingperson = new Missingperson();
		missingperson.setId(id);
		missingperson.setDetail(detail);
		missingperson.setTitle(title);
		missingperson.setCategory(category);
		missingpersonService.updateByPrimaryKeySelective(missingperson);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}
}
