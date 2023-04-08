package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Job;
import com.backend.uujob.mapper.JobMapper;
import com.backend.uujob.service.IJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class JobService extends ServiceImpl<JobMapper, Job> implements IJobService {
    @Resource
    private JobMapper jobMapper;

}
