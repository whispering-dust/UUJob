package com.backend.uujob.service.impl;

import com.backend.uujob.entity.UserSimilarity;
import com.backend.uujob.mapper.UserSimilarityMapper;
import com.backend.uujob.service.IUserSimilarityService;
import com.backend.uujob.utils.RecommendUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年05月31日14:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSimilarityService extends ServiceImpl<UserSimilarityMapper, UserSimilarity> implements IUserSimilarityService {

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
    public Integer isExistsUserSimilarity(UserSimilarity userSimilarity) {
        QueryWrapper<UserSimilarity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userSimilarity.getUserId()).eq("ref_user_id",userSimilarity.getRefUserId());
        List<UserSimilarity> list = list(wrapper);
        if(list.size()>0){
            return list.get(0).getId();
        }

        return -1;
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

    @Override
    public List<Integer> getTopNUser(Integer userId, Integer num) {

        // 1.查询出某个用户与其他用户的相似度列表
        List<UserSimilarity> userSimilarityList = listUserSimilarityByUId(userId);

        //2.打印输出
//        for (UserSimilarity userSimilarity: userSimilarityList) {
//            //System.out.println(userSimilarity.getUserId() + "\t" + userSimilarity.getRefUserId() + "\t" + userSimilarity.getSimilarity());
//        }

        // 3.获取与id为userId的用户的浏览行为最相似的前num个用户
        List<Integer> userIds = RecommendUtils.getSimilarityBetweenUsers(userId, userSimilarityList, num);

        // 4.打印输出
        System.out.println("与" + userId + "号用户最相似的前"+num+"个用户为：");
        for (Integer refUserId : userIds) {
            System.out.println(refUserId);
        }
        return userIds;
    }
}
