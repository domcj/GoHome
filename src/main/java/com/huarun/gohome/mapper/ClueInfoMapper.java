package com.huarun.gohome.mapper;

import java.util.List;

import com.huarun.gohome.model.ClueInfo;

public interface ClueInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClueInfo record);

    int insertSelective(ClueInfo record);

    ClueInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClueInfo record);

    int updateByPrimaryKey(ClueInfo record);

    List<ClueInfo> getClues(Integer id);

    List<ClueInfo> selectByMPId(Integer id);
}