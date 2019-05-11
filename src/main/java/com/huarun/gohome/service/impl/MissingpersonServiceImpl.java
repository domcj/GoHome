package com.huarun.gohome.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huarun.gohome.mapper.ClueInfoMapper;
import com.huarun.gohome.mapper.MissingpersonMapper;
import com.huarun.gohome.model.ClueInfo;
import com.huarun.gohome.model.Missingperson;
import com.huarun.gohome.service.MissingpersonService;

@Service
public class MissingpersonServiceImpl implements MissingpersonService {

	@Resource
	private MissingpersonMapper missingpersonMapper;
	@Resource
	private ClueInfoMapper clueInfoMapper;
	@Value("${image.prefix}")
	private String prefix;

	@Override
	public int insert(Missingperson record) {
		return missingpersonMapper.insert(record);
	}

	@Override
	public int insertSelective(Missingperson record) {
		return missingpersonMapper.insertSelective(record);
	}

	@Override
	public List<Missingperson> selectValidImage() {
		return missingpersonMapper.selectValidImage();
	}

	@Override
	public Missingperson selectByPrimaryKey(Integer id) {
		Missingperson missingperson = missingpersonMapper.selectByPrimaryKey(id);
		supplement(missingperson);
		return missingperson;
	}

	@Override
	public List<Missingperson> selectAddFace() {
		return missingpersonMapper.selectAddFace();
	}

	@Override
	public Integer getMaxId() {
		return missingpersonMapper.getMaxId();
	}

	@Override
	public List<Missingperson> getMissingPersonByUserId(Integer id) {
		List<Missingperson> records = missingpersonMapper.getMissingPersonByUserId(id);
		for (Missingperson record : records) {
			supplement(record);
		}
		return records;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return missingpersonMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Missingperson record) {
		return missingpersonMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Missingperson record) {
		return missingpersonMapper.updateByPrimaryKey(record);
	}

	private void supplement(Missingperson record) {
		List<ClueInfo> clueInfos = clueInfoMapper.selectByMPId(record.getId());
		for (ClueInfo clueInfo : clueInfos) {
			if (StringUtils.isNotEmpty(clueInfo.getImageurl())) {
				clueInfo.setImageurl(prefix + clueInfo.getImageurl() + "/1");
			}
		}
		record.setClueInfos(clueInfos);
		if (record.getImage()!=null&&!record.getImage().contains("http")) {
			record.setImage(prefix + record.getImage() + "/0");
		}
		return;
	}
}
