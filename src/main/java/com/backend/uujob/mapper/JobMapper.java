package com.backend.uujob.mapper;

import com.backend.uujob.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface JobMapper extends BaseMapper<Job> {
    List<Job> getById();
}
