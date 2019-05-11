package com.huarun.gohome.mapper;

import java.util.List;

import com.huarun.gohome.model.Attention;
import com.huarun.gohome.model.Missingperson;

public interface AttentionMapper {
    int insert(Attention record);

    int insertSelective(Attention record);

	List<Attention> getAttentionsByUserId(Integer userId);

	int removeAttention(Attention record);
}