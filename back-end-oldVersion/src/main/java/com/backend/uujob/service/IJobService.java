package com.backend.uujob.service;

import com.backend.uujob.controller.dto.JobDTO;
import com.backend.uujob.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IJobService extends IService<Job> {
    Job postJob(JobDTO jobDTO);
    List<Job> getPostJob(int id);
    List<Job> getAllJob();

    boolean deleteJob(int id);
}
