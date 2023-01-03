package com.backend.uujob.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.backend.uujob.common.Constants;
import com.backend.uujob.common.RoleEnum;
import com.backend.uujob.controller.dto.JobDTO;
import com.backend.uujob.entity.Job;
import com.backend.uujob.exception.ServiceException;
import com.backend.uujob.mapper.JobMapper;
import com.backend.uujob.service.IJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class JobService extends ServiceImpl<JobMapper, Job> implements IJobService {
    private final UserService userService;
    @Autowired
    public JobService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Job postJob(JobDTO jobDTO){
        int loginIdAsInt = jobDTO.getPublisher_id();
        if (RoleEnum.ROLE_RECRUITER.ordinal() == userService.getRole(loginIdAsInt)) { //检查用户是否有发布权限，后续可能用satoken的函数进行替代
            Job job = new Job(loginIdAsInt);
            BeanUtil.copyProperties(jobDTO, job, true);
            job.setStatus((short)1);//默认已审核
            job.setDate(new Date());
            save(job);
            return job;
        } else {
            throw new ServiceException(Constants.CODE_401, "用户权限不足");
        }
    }


    @Override
    public List<Job> getPostJob(int id) {
        //StpUtil.getLoginIdAsInt();
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("publisher_id",id);
        return list(wrapper);
    }

    @Override
    public boolean deleteJob(int id) {
        return removeById(id);
    }

    @Override
    public List<Job> getAllJob() {//修改 检查发布状态
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        return list(wrapper);
    }
}