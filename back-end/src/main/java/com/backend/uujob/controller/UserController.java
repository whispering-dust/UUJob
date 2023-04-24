package com.backend.uujob.controller;

import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.Profile;
import com.backend.uujob.entity.User;
import com.backend.uujob.entity.VO.JobVO;
import com.backend.uujob.enums.ApplStatusEnum;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IApplicationService;
import com.backend.uujob.service.IJobService;
import com.backend.uujob.service.IProfileService;
import com.backend.uujob.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

/**
 * @author mapleleaf
 * @date 2023年03月28日21:39
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IProfileService profileService;
    @Resource
    private IApplicationService applicationService;
    @Resource
    private IJobService jobService;

    /**
     * @description 通过id查找一个用户
     * @author mapleleaf
     * @date 2023/3/28 21:59
     */
    @GetMapping("")
    public Result getUserById(@RequestParam int id) {
            return Result.success(userService.getById(id));
    }

    @PostMapping("/registration")
    public Result registration(@RequestBody User user) {
        User targetUser = userService.getByUserName(user.getUserName());  //由于用户名唯一，需要检验用户名是否存在
        if(targetUser != null){
            return Result.error(Constants.CODE_500,"用户已存在");
        }


        user.setStatus(0);  //用户默认状态为未封禁
        userService.save(user);
        return Result.success(user.getId());
    }

    @PostMapping ("/login")
    public Result login(@RequestBody User user) {
        User targetUser = userService.getByUserName(user.getUserName());  //先检查用户是否存在

        if(targetUser == null){
            return Result.error(Constants.CODE_500,"用户不存在");
        }

        if(targetUser.getPassword().equals(user.getPassword())){  //若存在则检验密码是否正确
            return Result.success(targetUser.getId());
        }

        return Result.error(Constants.CODE_500,"密码错误");
    }

    @GetMapping("/profiles")
    public Result getProfileByUserId(@RequestParam int userId){
        Profile targetProtile = profileService.getByUserId(userId);
        if(targetProtile == null){
            return Result.error(Constants.CODE_500,"该用户尚未创建简历");
        }
        return Result.success(targetProtile);
    }

    @GetMapping("/applications")
    public Result getApplicationByUserId(@RequestParam int userId){
        Profile targetProtile = profileService.getByUserId(userId);  //先获取用户拥有的简历
        if(targetProtile == null){
            return Result.error(Constants.CODE_500,"该用户尚未创建简历");
        }

        List<Application> applicationList = applicationService.getByProfileId(targetProtile.getId());  //再根据简历查找他被投递到哪些岗位
        List<JobVO> jobList = new ArrayList<>();
        for(Application a : applicationList){
            Job j = jobService.getById(a.getJobId());

            JobVO ja = new JobVO();  //填入申请的基本信息
            ja.setTitle(j.getTitle());
            ja.setPosition(j.getPosition());
            ja.setLocation(j.getLocation());
            ja.setSalary(j.getSalary());
            ja.setApplicationDate(timeTransfer(a.getApplicationDate()));
            ja.setStatus(a.getStatus());
            if(ja.getStatus() != ApplStatusEnum.APPL_STATUS_SUBMIT.ordinal()){  //申请有结果才需要添加批复日期，否则不需要
                ja.setReviewDate(timeTransfer(a.getReviewDate()));
            }

            jobList.add(ja);
        }
        return Result.success(jobList);
    }

    @GetMapping("/jobs")
    public Result getJobByUserId(@RequestParam int userId){
        List<Job> jobList = jobService.getListByUserId(userId);
        if(jobList == null){
            return Result.error(Constants.CODE_500,"该用户尚未发布岗位");
        }
        return Result.success(jobList);
    }

}
