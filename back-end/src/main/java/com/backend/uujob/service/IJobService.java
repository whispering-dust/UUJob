package com.backend.uujob.service;

import com.backend.uujob.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IJobService extends IService<Job> {
    List<Job> Published();
}
