package com.backend.uujob.controller;

import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.Profile;
import com.backend.uujob.entity.User;
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
        User targetUser = userService.getByUserName(user.getUserName());
        user.setStatus(0);
        System.out.println(user.getId());
        if(targetUser != null){
            return Result.error(Constants.CODE_500,"用户已存在");
        }

        userService.save(user);
        System.out.println(user.getId());
        return Result.success(user.getId());
    }

    @PostMapping ("/login")
    public Result login(@RequestBody User user) {
        User targetUser = userService.getByUserName(user.getUserName());

        if(targetUser == null){
            return Result.error(Constants.CODE_500,"用户不存在");
        }

        if(targetUser.getPassword().equals(user.getPassword())){
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
        Profile targetProtile = profileService.getByUserId(userId);
        if(targetProtile == null){
            return Result.error(Constants.CODE_500,"该用户尚未创建简历");
        }

        List<Application> applicationList = applicationService.getByProfileId(targetProtile.getId());
        List<Job> jobList = new ArrayList<>();
        for(Application a : applicationList){
            jobList.add(jobService.getById(a.getJobId()));
        }
        return Result.success(jobList);
    }

}
