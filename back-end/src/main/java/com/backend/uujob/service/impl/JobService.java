package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Job;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.mapper.JobMapper;
import com.backend.uujob.service.IJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class JobService extends ServiceImpl<JobMapper, Job> implements IJobService {
    @Override
    public List<Job> getListByStatus(CensorStatusEnum censorStatusEnum) {
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("status", censorStatusEnum.getValue())  //满足状态的岗位才予以显示
                .orderByDesc("date");  //根据日期降序排列方便查找
        
        return list(jobQueryWrapper);
    }
    
    @Override
    public List<Job> getListByUserId(int userId) {
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("publisher_id", userId)  //根据用户id进行查找
                .orderByDesc("date");  //根据日期降序排列方便查找
        
        return list(jobQueryWrapper);
    }
    
    @Override
    public Job getJobById(int jobId) {
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("id", jobId)  //根据用户id进行查找
                .orderByDesc("date");  //根据日期降序排列方便查找
        return getOne(jobQueryWrapper);
    }
    
    @Override
    public List<Job> getListByPositionId(Integer positionId) {
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("position_id", positionId)
                .orderByDesc("date");  //根据日期降序排列方便查找;
        return list(jobQueryWrapper);
    }
    
}
