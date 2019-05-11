package com.huarun.gohome.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.huarun.gohome.mapper.ClueInfoMapper;
import com.huarun.gohome.model.ClueInfo;
import com.huarun.gohome.response.BaseResp;
import com.huarun.gohome.service.FacePPservice;
import com.huarun.gohome.util.DownFileUtil;

@RestController
public class ClueInfoController {

	@Resource
	private ClueInfoMapper clueInfoMapper;
	@Resource
	private FacePPservice facePPservice;
	@Value("${image.prefix}")
	private String prefix;

	@RequestMapping("/uploadSearch")
	public BaseResp upload(@RequestParam MultipartFile file) {
		BaseResp baseResp = new BaseResp();
		if (file==null||file.getSize()<5) {
			baseResp.setErrMsg("请上传图片");
			return baseResp;
		}
		String fileName = file.getOriginalFilename().split("\\.")[0]+".jpg";
		String result = null;
		String imageUril = DownFileUtil.downImage(file, fileName, "1");
		if (imageUril==null) {
			baseResp.setErrMsg("图片下载失败");
			return baseResp;
		}
		baseResp.setResult(BaseResp.SUCCESS);
		baseResp.setData(prefix+imageUril+"/1");
		return baseResp;
	}
	@RequestMapping("/addClue")
	public BaseResp addClue(HttpServletRequest request, @RequestParam(required = false) String loseName, @RequestParam(required = false) String loseIphone, @RequestParam(required = false) String loseSex, @RequestParam(required = false) String loseAddress, @RequestParam(required = false) String description, @RequestParam String imgUrl) {
		String userId = request.getHeader("user");
		String imgName = DownFileUtil.getFileName(imgUrl);
		byte[] files = DownFileUtil.loadImage(imgName, "1");
		BaseResp baseResp = new BaseResp();
		if (files==null||files.length<5) {
			baseResp.setErrMsg("图片不能为空");
			return baseResp;
		}
		Map<String, String> reulstMap = facePPservice.faceSearch(files);
		if (reulstMap.get("error")!=null) {
			baseResp.setErrMsg(reulstMap.get("error"));
			return baseResp;
		}

		ClueInfo dbClueInfo = new ClueInfo();
		dbClueInfo.setDescription(description);
		dbClueInfo.setLoseAddress(loseAddress);
		dbClueInfo.setLoseIphone(loseIphone);
		dbClueInfo.setLoseName(loseName);
		dbClueInfo.setLoseSex(loseSex);
		dbClueInfo.setMpId(Integer.valueOf(reulstMap.get("userId")));
		dbClueInfo.setuId(Integer.valueOf(userId));
		dbClueInfo.setStatus("0");
		dbClueInfo.setImageurl(imgName);
		dbClueInfo.setConfidence(reulstMap.get("confidence"));
		clueInfoMapper.insert(dbClueInfo);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}


	@RequestMapping("/getClueInfos")
	public List<ClueInfo> getClueInfos(HttpServletRequest request,  @RequestParam(required=false) Integer pageIndex, @RequestParam(required=false) Integer pageSize) {
		String userId = request.getHeader("user");
		if (pageIndex==null||pageSize==null) {
			pageIndex=1;
			pageSize=100;
		}
		PageHelper.startPage(pageIndex, pageSize);
		List<ClueInfo> clueInfos = clueInfoMapper.getClues(Integer.valueOf(userId));
		for (ClueInfo clueInfo : clueInfos) {
			if (StringUtils.isNotEmpty(clueInfo.getImageurl())) {
				clueInfo.setImageurl(prefix+clueInfo.getImageurl()+"/1");
			}
		}
		return clueInfos;
	}

	@RequestMapping("/getClueInfo/{id}")
	public ClueInfo getClueInfo(@PathVariable Integer id) {
		ClueInfo clueInfo = clueInfoMapper.selectByPrimaryKey(id);
		if (StringUtils.isNotEmpty(clueInfo.getImageurl())) {
			clueInfo.setImageurl(prefix+clueInfo.getImageurl()+"/1");
		}
		return clueInfo;
	}

	@RequestMapping("/removeClueInfo/{id}")
	public BaseResp removeClueInfo(@PathVariable Integer id) {
		BaseResp baseResp = new BaseResp();
		clueInfoMapper.deleteByPrimaryKey(id);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}

	@RequestMapping("/updateClueInfo")
	public BaseResp removeClueInfo(@RequestParam Integer id,@RequestParam(required = false) String status,@RequestParam(required = false) String loseSex,@RequestParam(required = false) String loseName,@RequestParam(required = false) String loseIphone,@RequestParam(required = false) String loseAddress,@RequestParam(required = false) String description) {
		BaseResp baseResp = new BaseResp();
		ClueInfo dbClueInfo = new ClueInfo();
		dbClueInfo.setId(id);
		dbClueInfo.setStatus(status);
		dbClueInfo.setLoseSex(loseSex);
		dbClueInfo.setLoseName(loseName);
		dbClueInfo.setLoseIphone(loseIphone);
		dbClueInfo.setLoseAddress(loseAddress);
		dbClueInfo.setDescription(description);
		clueInfoMapper.updateByPrimaryKeySelective(dbClueInfo);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}
}
