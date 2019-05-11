package com.huarun.gohome.service;

import java.util.List;

import com.huarun.gohome.model.Missingperson;

public interface MissingpersonService {
	int insert(Missingperson record);

	int insertSelective(Missingperson record);

	List<Missingperson> selectValidImage();

	Missingperson selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Missingperson record);

	int updateByPrimaryKey(Missingperson record);

	List<Missingperson> selectAddFace();

	Integer getMaxId();

	List<Missingperson> getMissingPersonByUserId(Integer id);

	int deleteByPrimaryKey(Integer id);
}
