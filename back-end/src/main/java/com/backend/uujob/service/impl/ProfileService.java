package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Profile;
import com.backend.uujob.mapper.ProfileMapper;
import com.backend.uujob.service.IProfileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static com.baomidou.mybatisplus.extension.toolkit.Db.getOne;

@Service
public class ProfileService extends ServiceImpl<ProfileMapper, Profile> implements IProfileService {
    @Resource
    private ProfileMapper profileMapper;

    @Override
    public Profile getByUserId(int userId){
        QueryWrapper<Profile> wrapper = new QueryWrapper<>();
        wrapper.eq("seeker_id",userId);
        return getOne(wrapper);
    }

}
