package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Application;
import com.backend.uujob.mapper.ApplicationMapper;
import com.backend.uujob.service.IApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {
    @Resource
    private ApplicationMapper applicationMapper;

    @Override
    public List<Application> getByJobId(int jobId){
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper
                .eq("job_id",jobId)
                .orderByDesc("application_date");
        return applicationMapper.selectList(wrapper);
    }

    @Override
    public List<Application> getByProfileId(int profileId){
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper
                .eq("profile_id",profileId)
                .orderByDesc("application_date");
        return applicationMapper.selectList(wrapper);
    }
}
