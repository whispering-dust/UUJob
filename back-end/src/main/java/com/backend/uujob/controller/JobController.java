package com.backend.uujob.controller;

import com.backend.uujob.controller.dto.ProfileCensorDTO;
import com.backend.uujob.entity.*;
import com.backend.uujob.entity.VO.ProfileVO;
import com.backend.uujob.enums.ApplStatusEnum;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

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
    @Resource
    private IProfileService profileService;

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

        jobService.updateById(job);
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
        application.setApplicationDate(new java.sql.Timestamp(System.currentTimeMillis()));
        applicationService.save(application);
        return Result.success();
    }

    @GetMapping("/applications")
    public Result getProfileListByJobId(@RequestParam int jobId){
        List<Application> applicationSubList =  applicationService.getByJobIdAndStatus(jobId, ApplStatusEnum.APPL_STATUS_SUBMIT.ordinal());  //通过岗位id获取所有待处理的申请信息

        List<ProfileVO> subList = new ArrayList<>();
        for(Application a : applicationSubList){  //通过申请信息获取所有简历id，以此获取简历信息
            Profile p = profileService.getById(a.getProfileId());

            ProfileVO pvo = new ProfileVO();
            pvo.setId(p.getId());
            pvo.setName(p.getName());
            pvo.setAge(p.getAge());
            pvo.setSex(p.getSex());
            pvo.setEmail(p.getEmail());
            pvo.setEducation(p.getEducation());
            pvo.setPhone(p.getPhone());
            pvo.setCollege(p.getCollege());
            pvo.setMajor(p.getMajor());
            pvo.setAdmissionDate(timeTransfer(p.getAdmissionDate()));
            pvo.setGraduationDate(timeTransfer(p.getGraduationDate()));
            pvo.setPersonalDescription(p.getPersonalDescription());
            pvo.setReward(p.getReward());
            pvo.setExpectedLocation(p.getExpectedLocation());
            pvo.setApplicationDate(timeTransfer(a.getApplicationDate()));

            subList.add(pvo);
        }

        List<Application> applicationPassList =  applicationService.getByJobIdAndStatus(jobId, ApplStatusEnum.APPL_STATUS_PASS.ordinal());  //通过岗位id获取所有通过申请信息

        List<ProfileVO> passList = new ArrayList<>();
        for(Application a : applicationPassList){  //通过申请信息获取所有简历id，以此获取简历信息
            Profile p = profileService.getById(a.getProfileId());

            ProfileVO pvo = new ProfileVO();
            pvo.setId(p.getId());
            pvo.setName(p.getName());
            pvo.setAge(p.getAge());
            pvo.setSex(p.getSex());
            pvo.setEmail(p.getEmail());
            pvo.setEducation(p.getEducation());
            pvo.setPhone(p.getPhone());
            pvo.setCollege(p.getCollege());
            pvo.setMajor(p.getMajor());
            pvo.setAdmissionDate(timeTransfer(p.getAdmissionDate()));
            pvo.setGraduationDate(timeTransfer(p.getGraduationDate()));
            pvo.setPersonalDescription(p.getPersonalDescription());
            pvo.setReward(p.getReward());
            pvo.setExpectedLocation(p.getExpectedLocation());
            pvo.setApplicationDate(timeTransfer(a.getApplicationDate()));
            pvo.setReviewDate(timeTransfer(a.getReviewDate()));

            passList.add(pvo);
        }

        ProfileCensorDTO censorDTO = new ProfileCensorDTO();
        censorDTO.setSubList(subList);
        censorDTO.setPassList(passList);

        return Result.success(censorDTO);
    }

    @PutMapping("/applications")
    public Result processApplication(@RequestBody Application application){
        //申请提交时默认状态为待审核
        application.setStatus(ApplStatusEnum.APPL_STATUS_SUBMIT.ordinal());
        //获取当前时间作为申请提交时间
        application.setApplicationDate(new java.sql.Timestamp(System.currentTimeMillis()));
        applicationService.updateById(application);
        return Result.success();
    }

}
