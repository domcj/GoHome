package com.huarun.gohome.service;

import java.util.List;

import com.huarun.gohome.model.ClueInfo;

public interface ClueInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(ClueInfo record);

    int insertSelective(ClueInfo record);

    ClueInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClueInfo record);

    int updateByPrimaryKey(ClueInfo record);

    List<ClueInfo> selectByMPId(Integer id);
}