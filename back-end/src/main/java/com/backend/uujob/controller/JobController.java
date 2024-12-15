package com.backend.uujob.controller;

import com.backend.uujob.dto.ActiveDTO;
import com.backend.uujob.dto.JobExamineDTO;
import com.backend.uujob.dto.ProfileCensorDTO;
import com.backend.uujob.entity.*;
import com.backend.uujob.enums.ApplStatusEnum;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.enums.RoleEnum;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.*;
import com.backend.uujob.vo.ProfileVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.backend.uujob.utils.FTPUtils.uploadFolder;
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
    
    @Resource
    private IActiveService activeService;
    @Resource
    private IPositionService positionService;
    
    @PostMapping("")
    public Result addJob(@RequestBody Job job) {
        //默认初始状态为待审核
        job.setStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT.getValue());
        //默认初始状态为待审核
        job.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
        
        //根据publisher-id确定company-name，并检查用户是否有发布岗位的权限
        User user = userService.getById(job.getPublisherId());
        if (!Objects.equals(user.getRole(), RoleEnum.ROLE_RECRUITER.getValue())) {
            return Result.error(Constants.CODE_401, "该用户没有发布岗位的权限");
        }
        Company company = companyService.getById(user.getCompanyId());
        job.setCompanyName(company.getName());
        
        
        if (jobService.save(job)) {
            return Result.success(job.getId());
        }
        return Result.error(Constants.CODE_400, "岗位存储失败");
    }
    
    @PutMapping("")
    public Result modifyJob(@RequestBody Job job) {
        //一旦对岗位信息进行修改就需要重新进行审核，所以状态为待审核
        if (Objects.equals(job.getStatus(), CensorStatusEnum.CENSOR_STATUS_SUBMIT.getValue())
                || Objects.equals(job.getStatus(), CensorStatusEnum.CENSOR_STATUS_PASS.getValue())) {
            job.setStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT.getValue());
        }
        
        if (jobService.updateById(job)) {
            return Result.success(job.getId());
        }
        return Result.error(Constants.CODE_400, "岗位更新失败");
    }
    
    @PutMapping("/terminate")
    public Result terminateJob(@RequestParam Integer id) {
        //根据id获取工作信息并将状态置为结束
        Job job = jobService.getJobById(id);
        job.setStatus(CensorStatusEnum.CENSOR_STATUS_TERMINATE.getValue());
        
        if (jobService.updateById(job)) {
            return Result.success();
        }
        return Result.error(Constants.CODE_400, "岗位更新失败");
    }
    
    
    @GetMapping("")
    public Result getJob(@RequestParam int id) {
        Job target = jobService.getById(id);
        if (target == null) {
            return Result.error(Constants.CODE_500, "该岗位不存在");
        }
        return Result.success(target);
    }
    
    @GetMapping("/basis")
    public Result getJobBasisList() {
        List<Job> jobList = jobService.getListByStatus(CensorStatusEnum.CENSOR_STATUS_PASS);
        jobList.addAll(jobService.getListByStatus(CensorStatusEnum.CENSOR_STATUS_TERMINATE));
        if (jobList == null) {
            return Result.error(Constants.CODE_500, "没有岗位信息");
        }
        return Result.success(jobList);
    }
    
    @PostMapping("/applications")
    public Result applyForJob(@RequestBody Application application) {
        //检查是否已经投过该岗位
        int userId = profileService.getUserIdByProfileId(application.getProfileId());
        List<Profile> profileList = profileService.getListByUserId(userId);//获取用户的所有简历
        for (Profile p : profileList) {
            Application tmp = applicationService.getByProfileId(p.getId());//查找简历对应的申请
            if (Objects.equals(tmp.getJobId(), application.getJobId())) {//如果申请中的jobId和新提交的一致，说明重复提交
                return Result.error(Constants.CODE_500, "已经投递过该岗位");
            }
        }
        
        //复制一份简历作为被投递的简历
        int targetProfileId = application.getProfileId();
        Profile newProfile = profileService.copyGenerate(targetProfileId);
        application.setProfileId(newProfile.getId());
        //申请提交时默认状态为待审核
        application.setStatus(ApplStatusEnum.APPL_STATUS_SUBMIT.getValue());
        //获取当前时间作为申请提交时间
        application.setApplicationDate(new java.sql.Timestamp(System.currentTimeMillis()));
        if (applicationService.save(application)) {
            return Result.success(application.getId());
        }
        return Result.error(Constants.CODE_500, "简历投递失败");
    }
    
    @DeleteMapping("/applications")
    public Result revokeApplication(@RequestParam Integer profileId) {
        if (profileService.removeById(profileId)) {
            return Result.success();
        }
        return Result.error();
    }
    
    @GetMapping("/applications")
    public Result getProfileListByJobId(@RequestParam int jobId) {
        List<Application> applicationSubList = applicationService.getByJobIdAndStatus(jobId, ApplStatusEnum.APPL_STATUS_SUBMIT.getValue());  //通过岗位id获取所有待处理的申请信息
        
        List<ProfileVO> subList = new ArrayList<>();
        for (Application a : applicationSubList) {  //通过申请信息获取所有简历id，以此获取简历信息
            Profile p = profileService.getById(a.getProfileId());
            
            ProfileVO pvo = new ProfileVO();
            pvo.setProfileId(p.getId());
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
            pvo.setPhotoUrl(p.getPhotoUrl());
            pvo.setAnnexUrl(a.getAnnexUrl());
            pvo.setApplicationId(a.getId());
            pvo.setApplicationDate(timeTransfer(a.getApplicationDate()));
            
            subList.add(pvo);
        }
        
        List<Application> applicationPassList = applicationService.getByJobIdAndStatus(jobId, ApplStatusEnum.APPL_STATUS_PASS.getValue());  //通过岗位id获取所有通过申请信息
        
        List<ProfileVO> passList = new ArrayList<>();
        for (Application a : applicationPassList) {  //通过申请信息获取所有简历id，以此获取简历信息
            Profile p = profileService.getById(a.getProfileId());
            
            ProfileVO pvo = new ProfileVO();
            pvo.setProfileId(p.getId());
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
            pvo.setPhotoUrl(p.getPhotoUrl());
            pvo.setAnnexUrl(a.getAnnexUrl());
            pvo.setApplicationId(a.getId());
            pvo.setApplicationDate(timeTransfer(a.getApplicationDate()));
            pvo.setReviewDate(timeTransfer(a.getReviewDate()));
            
            passList.add(pvo);
        }
        
        ProfileCensorDTO censorDTO = new ProfileCensorDTO();
        censorDTO.setSubList(subList);
        censorDTO.setPassList(passList);
        
        if (censorDTO == null) {
            return Result.error(Constants.CODE_500, "数据对象创建失败");
        }
        return Result.success(censorDTO);
    }
    
    @PutMapping("/applications")
    public Result processApplication(@RequestBody Application application) {
        //根据前端传来的profileId来查找application
        Application targetApplication = applicationService.getByProfileId(application.getProfileId());
        targetApplication.setStatus(application.getStatus());
        targetApplication.setReviewDate(new java.sql.Timestamp(System.currentTimeMillis()));//设置当前时间为审查时间
        
        if (applicationService.updateById(targetApplication)) {
            return Result.success(targetApplication.getId());
        }
        return Result.error();
    }
    
    @GetMapping("/unaudited")
    public Result getJobUnaudited() {
        List<Job> jobList = jobService.getListByStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT);
        List<JobExamineDTO> jobExamineDTOList = new ArrayList<>();
        int num = 0;
        for (Job j : jobList) {
            JobExamineDTO jobExamineDTO = new JobExamineDTO();
            jobExamineDTO.setId(j.getId());
            jobExamineDTO.setTitle(j.getTitle());
            jobExamineDTO.setDescription(j.getDescription());
            jobExamineDTO.setPosition(j.getPosition());
            jobExamineDTO.setLocation(j.getLocation());
            jobExamineDTO.setSalary(j.getSalary());
            jobExamineDTO.setEducation(j.getEducation());
            jobExamineDTO.setCompanyName(j.getCompanyName());
            jobExamineDTO.setPublisherId(j.getPublisherId());
            jobExamineDTO.setUserName(userService.getNameById(j.getPublisherId()));
            
            jobExamineDTOList.add(jobExamineDTO);
            num++;
            if (num >= 30) {
                break;
            }
        }
        
        return Result.success(jobExamineDTOList);
    }
    
    @PutMapping("/examinations")
    public Result examineJob(@RequestBody Job job) {
        job.setDate(new java.sql.Timestamp(System.currentTimeMillis()));  //获取当前时间作为工作的发布时间
        if (jobService.updateById(job)) {
            return Result.success();
        }
        return Result.error();
    }
    
    
    @PutMapping("/hits")
    public Result updateHits(@RequestBody ActiveDTO activeDTO) {
        Active active = new Active();
        active.setUserId(activeDTO.getUserId());
        
        //根据job获取职位id
        Job targetJob = jobService.getJobById(activeDTO.getJobId());
        active.setPositionId(targetJob.getPositionId());
        
        //更新数据
        int targetHits = activeService.saveUserActive(active);
        if (targetHits >= 0) {
            return Result.success(targetHits);
        }
        return Result.error(Constants.CODE_500, "点击数据更新失败，没写入数据库");
    }
    
    @GetMapping("/positions")
    public Result getPositionList() {
        List<Position> list = positionService.getPositionList();
        
        if (!list.isEmpty()) {
            return Result.success(list);
        }
        return Result.error(Constants.CODE_500, "系统错误");
    }
    
    @PostMapping("/upload-files")
    public Result uploadJobTemplate(
            @RequestParam("jobId") Integer jobId,
            @RequestParam("template") MultipartFile template
    ) {
        Job job = jobService.getById(jobId);
        if (job == null) {
            return Result.error(Constants.CODE_400, "该岗位不存在");
        }
        
        String url = uploadFolder(template);
        if (url != null) {
            job.setTemplateUrl(url);
            jobService.updateById(job);
            return Result.success(url);
        }
        return Result.error(Constants.CODE_500, "文件上传失败");
    }
    
    @PostMapping("/applications/upload-files")
    public Result uploadApplicationAnnex(
            @RequestParam("applicationId") Integer applicationId,
            @RequestParam("annex") MultipartFile annex
    ) {
        Application application = applicationService.getById(applicationId);
        if (application == null) {
            return Result.error(Constants.CODE_400, "该申请不存在");
        }
        
        String url = uploadFolder(annex);
        if (url != null) {
            application.setAnnexUrl(url);
            applicationService.updateById(application);
            return Result.success(url);
        }
        return Result.error(Constants.CODE_500, "文件上传失败");
    }
    
    @DeleteMapping("")
    public Result deleteJob(@RequestParam int jobId) {
        if (jobService.removeById(jobId)) {
            return Result.success();
        }
        return Result.error(Constants.CODE_500, "数据库操作失败，可能是没有该id的job");
    }
    
    @GetMapping("/companies")
    public Result getCompany(@RequestParam String companyName) {
        Company target = companyService.getCompanyByName(companyName);
        if (target != null) {
            return Result.success(target);
        }
        return Result.error(Constants.CODE_500, "没有该公司信息");
    }
}
