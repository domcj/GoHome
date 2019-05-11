package com.huarun.gohome.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.huarun.gohome.model.ClueInfo;
import com.huarun.gohome.model.Missingperson;
import com.huarun.gohome.response.BaseResp;
import com.huarun.gohome.service.ClueInfoService;
import com.huarun.gohome.service.MissingpersonService;
import com.huarun.gohome.util.DownFileUtil;

@RestController
public class HandleAppController {

	public volatile int inc = 0;
	private Logger logger = LoggerFactory.getLogger(HandleAppController.class);
	@Resource
	private MissingpersonService missingpersonService;
	@Resource
	private ClueInfoService clueInfoService;
	@Value("${image.prefix}")
	private String prefix;

	@RequestMapping("/getMissingPerson")
	public List<Missingperson> getMissingPerson(@RequestParam(required=false) Integer pageIndex, @RequestParam(required=false) Integer pageSize) {
		logger.info(pageIndex+","+pageSize);
		if (pageIndex==null||pageSize==null) {
			pageIndex=1;
			pageSize=100;
		}
		PageHelper.startPage(pageIndex, pageSize);
		List<Missingperson> missingpersons = missingpersonService.selectValidImage();
		for (Missingperson missingperson : missingpersons) {
			List<ClueInfo> clueInfos = clueInfoService.selectByMPId(missingperson.getId());
			if (!CollectionUtils.isEmpty(clueInfos)) {
				missingperson.setClueInfos(clueInfos);
			}
		}
		return missingpersons;
	}

//	@RequestMapping("/downImage")
//	public List<Missingperson> downImage() {
//		ExecutorService service = Executors.newFixedThreadPool(6);
//		List<Missingperson> missingpersons = missingpersonService.selectValidImage();
//		for (Missingperson missingperson : missingpersons) {
//			service.submit(new Runnable() {
//				@Override
//				public void run() {
//					if (missingperson==null||StringUtils.isEmpty(missingperson.getImage())) {
//						return;
//					}
//					String image = missingperson.getImage();
//					String[] split = image.split("/");
//					String fileName = split[split.length - 1];
//					String fileprox = ".jpg";
//					if (image.contains("png"))  {
//						fileprox = ".png";
//					}
//					String filePath = "D:\\picture\\"+inc+fileprox;
//					DownFileUtil.downloadPicture(image, filePath);
//					inc++;
//				}
//			});
//		}
//		System.out.println(missingpersons.size());
//		return missingpersons;
//	}

	@RequestMapping("/addAppClue/{id}")
	public BaseResp addClue(@PathVariable Integer id, @RequestParam(required = false) String loseName, @RequestParam(required = false) String loseIphone, @RequestParam(required = false) String loseSex, @RequestParam(required = false) String loseAddress, @RequestParam(required = false) String description, @RequestParam(required = false) String confidence, @RequestParam String imgUrl) {
		BaseResp baseResp = new BaseResp();
		String imgName = DownFileUtil.getFileName(imgUrl);
		byte[] files = DownFileUtil.loadImage(imgName, "1");
		if (files==null||files.length<5) {
			baseResp.setErrMsg("图片不能为空");
			return baseResp;
		}
		ClueInfo dbClueInfo = new ClueInfo();
		dbClueInfo.setDescription(description);
		dbClueInfo.setLoseAddress(loseAddress);
		dbClueInfo.setLoseIphone(loseIphone);
		dbClueInfo.setLoseName(loseName);
		dbClueInfo.setLoseSex(loseSex);
		dbClueInfo.setMpId(id);
		dbClueInfo.setuId(1);
		dbClueInfo.setStatus("0");
		dbClueInfo.setImageurl(imgName);
		dbClueInfo.setConfidence(confidence);
		clueInfoService.insert(dbClueInfo);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}
	@RequestMapping("/uploadClue")
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
}
