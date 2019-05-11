package com.huarun.gohome.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huarun.gohome.mapper.ClueInfoMapper;
import com.huarun.gohome.model.ClueInfo;
import com.huarun.gohome.service.ClueInfoService;

@Service
public class ClueInfoServiceImpl implements ClueInfoService {

    @Resource
    private ClueInfoMapper clueInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return clueInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ClueInfo record) {
        return clueInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(ClueInfo record) {
        return clueInfoMapper.insertSelective(record);
    }

    @Override
    public ClueInfo selectByPrimaryKey(Integer id) {
        return clueInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ClueInfo record) {
        return clueInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ClueInfo record) {
        return clueInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ClueInfo> selectByMPId(Integer id) {
        return clueInfoMapper.selectByMPId(id);
    }
}
