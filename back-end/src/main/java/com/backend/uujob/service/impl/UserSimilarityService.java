package com.backend.uujob.service.impl;

import com.backend.uujob.entity.UserSimilarity;
import com.backend.uujob.mapper.UserSimilarityMapper;
import com.backend.uujob.service.IUserSimilarityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年05月31日14:38
 */
@Service
public class UserSimilarityService extends ServiceImpl<UserSimilarityMapper, UserSimilarity> implements IUserSimilarityService {
    @Resource
    private UserSimilarityMapper userSimilarityMapper;

    @Override
    public boolean saveUserSimilarity(UserSimilarity userSimilarity) {
        if(save(userSimilarity)){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserSimilarity(UserSimilarity userSimilarity) {
        if(updateById(userSimilarity)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistsUserSimilarity(UserSimilarity userSimilarity) {
        QueryWrapper<UserSimilarity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userSimilarity.getUserId()).eq("ref_user_id",userSimilarity.getRefUserId());
        List<UserSimilarity> list = list(wrapper);

        if(list.size()>0){
            return true;
        }

        return false;
    }

    @Override
    public List<UserSimilarity> listUserSimilarityByUId(Integer userId) {
        if (userId == null) {
            return null;
        }
        QueryWrapper<UserSimilarity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<UserSimilarity> userSimilarityList = list(wrapper);

        return userSimilarityList;

    }
}
