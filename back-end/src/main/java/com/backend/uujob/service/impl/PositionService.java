package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Position;
import com.backend.uujob.mapper.PositionMapper;
import com.backend.uujob.service.IPositionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年05月30日16:33
 */
@Service
public class PositionService extends ServiceImpl<PositionMapper, Position> implements IPositionService {
    @Resource
    private PositionMapper positionMapper;

    @Override
    public List<Position> getPositionList() {
        QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();

        return list(positionQueryWrapper);
    }
}
