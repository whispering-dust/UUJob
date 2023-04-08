package com.backend.uujob.service;

import com.backend.uujob.entity.Application;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IApplicationService extends IService<Application> {
    List<Application> getByJobId(int jobId);

    List<Application> getByProfileId(int profileId);
}
