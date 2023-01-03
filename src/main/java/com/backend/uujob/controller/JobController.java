package com.backend.uujob.controller;

import com.backend.uujob.common.Result;
import com.backend.uujob.controller.dto.JobDTO;
import com.backend.uujob.exception.ServiceException;
import com.backend.uujob.service.IJobService;
import com.backend.uujob.service.impl.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/job")
public class JobController {

    private final IJobService jobService;
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * 获取所有招聘信息
     * @return 获取结果
     */
    @GetMapping("/allJob")
    public Result allJob() {
        try {
            return Result.success(jobService.getAllJob());
        } catch (ServiceException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 获取用户发布的招聘信息
     * @param id 用户ID
     * @return 获取结果
     */
    @GetMapping("/myPost")
    public Result myPost(@RequestParam int id){
        try{
            return Result.success(jobService.getPostJob(id));
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 发布招聘信息
     * @param jobDTO 招聘信息数据传输对象
     * @return 发布结果
     */
    @PostMapping("/post")
    public Result postJob(@RequestBody JobDTO jobDTO){//有效数据仅含title、position、salary、description
        try{
            jobService.postJob(jobDTO);
            return Result.success(jobDTO);
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 删除招聘信息
     * @param jobId 招聘信息ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result deleteJob(@RequestParam int jobId){
        try{
            return Result.success(jobService.deleteJob(jobId));
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
