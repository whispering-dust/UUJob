package com.backend.uujob.controller;

import com.backend.uujob.common.Result;
import com.backend.uujob.controller.dto.ApplicationDTO;
import com.backend.uujob.controller.dto.ResumeDTO;
import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Resume;
import com.backend.uujob.exception.ServiceException;
import com.backend.uujob.service.IResumeService;
import com.backend.uujob.service.impl.ApplicationService;
import com.backend.uujob.service.impl.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/resume")
public class ResumeController {
    private final IResumeService resumeService;
    private final ApplicationService applicationService;
    @Autowired
    public ResumeController(ResumeService resumeService, ApplicationService applicationService) {
        this.resumeService = resumeService;
        this.applicationService=applicationService;
    }

    /**
     * 投递简历
     * @param job_id 投递招聘信息的ID
     * @param resumeDTO 用户的简历信息
     * @return 投递结果
     */
    @Transactional
    @PostMapping
    public Result postResume(@RequestParam Integer job_id, @RequestBody ResumeDTO resumeDTO){//id和seeker_id会被忽略
        try{
            Resume resume = resumeService.postResume(resumeDTO);
            ApplicationDTO a = new ApplicationDTO(resume.getId(), job_id,new Date());
            Application application = applicationService.postApplication(a);
            return Result.success(application);
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 获取用户所投递的简历
     * @param id 用户ID
     * @return 获取结果
     */
    @GetMapping
    public Result getResume(@RequestParam int id){
        return Result.success(resumeService.getResume(id));
    }

    /**
     * 获取某个招聘信息下所接收到的简历
     * @param id 招聘信息ID
     * @return 获取结果
     */
    @GetMapping("/jobResume")
    public Result getJobResume(@RequestParam int id) {
        try {
            return Result.success(resumeService.getJobResume(applicationService.getJobResume(id)));
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 删除简历
     * @param resumeId 简历ID
     * @return 删除结果
     */
    public boolean deleteResume(Integer resumeId){
        return applicationService.deleteApplicationById(resumeId);
    }

}
