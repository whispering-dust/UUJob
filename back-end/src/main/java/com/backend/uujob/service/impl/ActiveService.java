package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Active;
import com.backend.uujob.mapper.ActiveMapper;
import com.backend.uujob.service.IActiveService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年05月30日17:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActiveService extends ServiceImpl<ActiveMapper, Active> implements IActiveService {
    
    @Resource
    private ActiveMapper activeMapper;
    
    @Override
    public List<Active> listAllUserActive() {
        QueryWrapper<Active> wrapper = new QueryWrapper<>();
        return list(wrapper);
    }
    
    @Override
    public int saveUserActive(Active userActive) {
        QueryWrapper<Active> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userActive.getUserId()).eq("position_id", userActive.getPositionId());
        List<Active> activeList = list(wrapper);
        // 1.先判断数据库中是否存在当前用户的浏览记录
        int rows = activeList.size();
        
        // 2.不存在就添加
        if (rows < 1) { // 不存在
            userActive.setHits(1); // 不存在记录的话那肯定是第一次访问，那点击量肯定是1
            save(userActive);
        } else { // 已经存在
            // 3.存在则先把当前用户对当前二级类目的点击量取出来+1
            int hits = activeList.get(0).getHits();
            int id = activeList.get(0).getId();
            // 4.然后在更新用户的浏览记录
            hits++;
            userActive.setHits(hits);
            userActive.setId(id);
            updateById(userActive);
        }
        
        QueryWrapper<Active> newWrapper = new QueryWrapper<>();
        newWrapper.eq("user_id", userActive.getUserId()).eq("position_id", userActive.getPositionId());
        List<Active> newList = list(newWrapper);
        if (CollectionUtils.isNotEmpty(newList)) {
            return newList.get(0).getHits();
        }
        return -1;
    }
}
