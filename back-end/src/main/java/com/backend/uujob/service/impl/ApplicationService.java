package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Application;
import com.backend.uujob.mapper.ApplicationMapper;
import com.backend.uujob.service.IApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {
    @Resource
    private ApplicationMapper applicationMapper;
    
    @Override
    public List<Application> getByJobIdAndStatus(int jobId, int status) {
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("job_id", jobId)
                .eq("status", status)
                .orderByDesc("application_date");
        return applicationMapper.selectList(wrapper);
    }
    
    @Override
    public Application getByProfileId(int profileId) {
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("profile_id", profileId)
                .orderByDesc("application_date");
        List<Application> list = list(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list(wrapper).get(0);
        }
    }
    
    @Override
    public Boolean checkApplicationExist(Integer profileId, Integer jobId) {
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("profile_id", profileId)
                .eq("job_id", jobId);
        if (list(wrapper).isEmpty()) {
            return false;
        }
        return true;
    }
}
