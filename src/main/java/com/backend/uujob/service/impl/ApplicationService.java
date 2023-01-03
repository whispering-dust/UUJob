package com.backend.uujob.service.impl;

import com.backend.uujob.controller.dto.ApplicationDTO;
import com.backend.uujob.entity.Application;
import com.backend.uujob.mapper.ApplicationMapper;
import com.backend.uujob.service.IApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {
    @Override
    public Application postApplication(ApplicationDTO applicationDTO) {//提交简历申请
        //保存application
        Application application=new Application(applicationDTO.getResume_id(),applicationDTO.getJob_id(),applicationDTO.getDate());
        save(application);
        return application;
    }

    @Override
    public boolean deleteApplicationById(int resumeId) {
       return removeById(resumeId);
    }


    @Override
    public List<Application> getJobResume(int jobId) {
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        wrapper.eq("job_id", jobId);
        return list(wrapper);
    }
}
