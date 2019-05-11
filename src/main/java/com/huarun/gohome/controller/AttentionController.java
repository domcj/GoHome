package com.huarun.gohome.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.huarun.gohome.mapper.AttentionMapper;
import com.huarun.gohome.model.Attention;
import com.huarun.gohome.model.Missingperson;
import com.huarun.gohome.response.BaseResp;
import com.huarun.gohome.service.ClueInfoService;
import com.huarun.gohome.service.MissingpersonService;

@RestController
public class AttentionController {

	@Resource
	private AttentionMapper attentionMapper;
	@Resource
	private MissingpersonService missingpersonService;
	@Resource
	private ClueInfoService clueInfoService;
	@Value("${image.prefix}")
	private String prefix;

	@RequestMapping("/getAttentions")
	public List<Missingperson> getAttentions(HttpServletRequest request, @RequestParam(required=false) Integer pageIndex, @RequestParam(required=false) Integer pageSize) {
		String userId = request.getHeader("user");
		if (pageIndex==null||pageSize==null) {
			pageIndex=1;
			pageSize=10;
		}
		PageHelper.startPage(pageIndex, pageSize);
		List<Attention> attentions = attentionMapper.getAttentionsByUserId(Integer.valueOf(userId));
		List<Missingperson> missingpeoples = new ArrayList<>();
		for (Attention attention : attentions) {
			Missingperson missingperson = missingpersonService.selectByPrimaryKey(attention.getMpId());
			if (missingperson!=null) {
				missingpeoples.add(missingperson);
//				List<ClueInfo> clueInfos = clueInfoService.selectByMPId(missingperson.getId());
//				for (ClueInfo clueInfo : clueInfos) {
//					if (StringUtils.isNotEmpty(clueInfo.getImageurl())) {
//						clueInfo.setImageurl(prefix+clueInfo.getImageurl()+"/1");
//					}
//				}
//				missingperson.setClueInfos(clueInfos);
//				missingperson.setImage(prefix+missingperson.getImage()+"/0");
			}
		}
		return missingpeoples;
	}

	@RequestMapping("/removeAttention/{id}")
	public BaseResp removeAttention(HttpServletRequest request, @PathVariable Integer id) {
		BaseResp baseResp = new BaseResp();
		String userId = request.getHeader("user");
		Attention attention = new Attention();
		attention.setuId(Integer.valueOf(userId));
		attention.setMpId(id);
		attentionMapper.removeAttention(attention);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}

	@RequestMapping("/addAttention/{id}")
	public BaseResp addAttention(HttpServletRequest request, @PathVariable Integer id) {
		BaseResp baseResp = new BaseResp();
		String userId = request.getHeader("user");
		Attention attention = new Attention();
		attention.setuId(Integer.valueOf(userId));
		attention.setMpId(id);
		attentionMapper.insert(attention);
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}

	@RequestMapping("/findAttentions")
	public List<Missingperson> addAttention(HttpServletRequest request, @RequestParam(required=false) Integer pageIndex, @RequestParam(required=false) Integer pageSize) {
		String userId = request.getHeader("user");
		if (pageIndex==null||pageSize==null) {
			pageIndex=1;
			pageSize=10;
		}
		PageHelper.startPage(pageIndex, pageSize);
		return missingpersonService.selectAttention(Integer.valueOf(userId));
	}
}
