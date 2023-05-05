package com.backend.uujob.service;

import com.backend.uujob.entity.Application;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jeffreyning.mybatisplus.service.IMppService;

import java.util.List;

public interface IApplicationService extends IMppService<Application> {
    List<Application> getByJobIdAndStatus(int jobId, int status);

    List<Application> getByProfileId(int profileId);
}
