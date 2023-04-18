package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Job;
import com.backend.uujob.enums.StatusEnum;
import com.backend.uujob.mapper.JobMapper;
import com.backend.uujob.service.IJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService extends ServiceImpl<JobMapper, Job> implements IJobService {
    @Resource
    private JobMapper jobMapper;
    @Override
    public List<Job> Published(){
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("status", StatusEnum.STATUS_PASS.ordinal());
        return jobMapper.selectList(wrapper);
    }

}
