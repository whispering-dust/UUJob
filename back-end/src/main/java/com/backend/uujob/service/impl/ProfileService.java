package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Profile;
import com.backend.uujob.mapper.ProfileMapper;
import com.backend.uujob.service.IProfileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.baomidou.mybatisplus.extension.toolkit.Db.getOne;

@Service
public class ProfileService extends ServiceImpl<ProfileMapper, Profile> implements IProfileService {
    @Resource
    private ProfileMapper profileMapper;

    @Override
    public Profile getByUserId(int userId){
        QueryWrapper<Profile> wrapper = new QueryWrapper<>();
        wrapper.eq("seeker_id",userId)
                .orderByAsc("profile_type");  //根据日期降序排列,获取最新的简历

        Profile res =list(wrapper).get(0);
        return res;
    }

    @Override
    public List<Profile> getListByUserId(Integer userId) {
        QueryWrapper<Profile> wrapper = new QueryWrapper<>();
        wrapper.eq("seeker_id",userId);  //根据日期降序排列,获取最新的简历

        return list(wrapper);
    }

    @Override
    public Profile copyGenerate(Integer profileId) {
        // 获取当前的简历
        QueryWrapper<Profile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",profileId)
                .orderByAsc("profile_type");  //根据日期降序排列,获取最新的简历
        Profile targetProfile = getOne(wrapper);

        targetProfile.setProfileType(2);
        targetProfile.setId(null);
        save(targetProfile);

        return targetProfile;
    }

    @Override
    public Integer getUserIdByProfileId(Integer profileId) {
        QueryWrapper<Profile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",profileId);
        Profile targetProfile = getOne(wrapper);
        return targetProfile.getSeekerId();
    }


}
