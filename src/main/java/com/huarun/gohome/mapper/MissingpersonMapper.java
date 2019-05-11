package com.huarun.gohome.mapper;

import java.util.List;

import com.huarun.gohome.model.Missingperson;

public interface MissingpersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Missingperson record);

    int insertSelective(Missingperson record);

    Missingperson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Missingperson record);

    int updateByPrimaryKey(Missingperson record);

	List<Missingperson> selectValidImage();

	List<Missingperson> selectAttention(Integer userId);

    List<Missingperson> selectAddFace();

    Integer getMaxId();

    List<Missingperson> getMissingPersonByUserId(Integer id);
}