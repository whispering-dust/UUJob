package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Job;
import com.backend.uujob.enums.CensorStatusEnum;
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
    public List<Job> getListByStatus(CensorStatusEnum censorStatusEnum){
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper
                .eq("status", censorStatusEnum.ordinal())  //满足状态的岗位才予以显示
                .orderByDesc("date");  //根据日期降序排列方便查找

        return list(jobQueryWrapper);
    }

    @Override
    public List<Job> getListByUserId(int userId){
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper
                .eq("publisher_id", userId)  //根据用户id进行查找
                .orderByDesc("date");  //根据日期降序排列方便查找

        return list(jobQueryWrapper);
    }

    @Override
    public Job getJobById(int jobId) {
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper
                .eq("id", jobId)  //根据用户id进行查找
                .orderByDesc("date");  //根据日期降序排列方便查找
        return getOne(jobQueryWrapper);
    }

}
