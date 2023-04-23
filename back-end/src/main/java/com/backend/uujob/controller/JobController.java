package com.backend.uujob.controller;

import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Company;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.User;
import com.backend.uujob.enums.ApplStatusEnum;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IApplicationService;
import com.backend.uujob.service.ICompanyService;
import com.backend.uujob.service.IJobService;
import com.backend.uujob.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Resource
    private IJobService jobService;
    @Resource
    private IUserService userService;
    @Resource
    private ICompanyService companyService;
    @Resource
    private IApplicationService applicationService;

    @PostMapping("")
    public Result addJob(@RequestBody Job job){
        //默认初始状态为待审核
        job.setStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT.ordinal());

        //根据publisher-id确定company-name
        User user = userService.getById(job.getPublisherId());
        Company company = companyService.getById(user.getCompanyId());
        if(company == null){
            return Result.error(Constants.CODE_401, "该用户没有发布岗位的权限");
        }
        job.setCompanyName(company.getName());

        jobService.save(job);
        return Result.success(job.getId());
    }

    @PutMapping("")
    public Result modifyJob(@RequestBody Job job){
        //一旦对岗位信息进行修改就需要重新进行审核，所以状态为待审核
        job.setStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT.ordinal());

        //由于岗位的公司信息不会变更，因此找到此前数据库中的公司信息插入即可
        Job targetJob = jobService.getById(job.getId());
        job.setCompanyName(targetJob.getCompanyName());

        jobService.save(job);
        return Result.success(job.getId());
    }


    @GetMapping("")
    public Result getJob(@RequestParam int id){
        return Result.success(jobService.getById(id));
    }

    @GetMapping("/basis")
    public Result getJobBasisList(){
        return Result.success(jobService.getPublishedList());
    }

    @PostMapping("/applications")
    public Result applyForJob(@RequestBody Application application){
        //申请提交时默认状态为待审核
        application.setStatus(ApplStatusEnum.APPL_STATUS_SUBMIT.ordinal());
        //获取当前时间作为申请提交时间
        application.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
        applicationService.save(application);
        return Result.success();
    }

}
