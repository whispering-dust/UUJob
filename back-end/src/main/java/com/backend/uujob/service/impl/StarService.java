package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Star;
import com.backend.uujob.mapper.StarMapper;
import com.backend.uujob.service.IStarService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class StarService extends ServiceImpl<StarMapper, Star> implements IStarService {
    @Override
    public Star getByAll(Star star) {
        QueryWrapper<Star> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", star.getUserId())
                .eq("target_id", star.getTargetId())
                .eq("star_type", star.getStarType());
        
        return getOne(wrapper);
    }
    
    @Override
    public List<Star> getByUserId(int userId) {
        QueryWrapper<Star> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        
        return list(wrapper);
    }
}
