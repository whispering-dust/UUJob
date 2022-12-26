package com.backend.uujob.controller;

import com.backend.uujob.common.Result;
import com.backend.uujob.controller.dto.ApplicationDTO;
import com.backend.uujob.controller.dto.ProfileDTO;
import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Profile;
import com.backend.uujob.exception.ServiceException;
import com.backend.uujob.service.IProfileService;
import com.backend.uujob.service.impl.ApplicationService;
import com.backend.uujob.service.impl.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final IProfileService profileService;
    private final ApplicationService applicationService;
    @Autowired
    public ProfileController(ProfileService profileService,ApplicationService applicationService) {
        this.profileService = profileService;
        this.applicationService=applicationService;
    }

    /**
     * 投递简历
     * @param recruitTable_id 投递招聘信息的ID
     * @param profileDTO 用户的简历信息
     * @return 投递结果
     */
    @Transactional
    @PostMapping
    public Result postProfile(@RequestParam Integer recruitTable_id, @RequestBody ProfileDTO profileDTO){//id和seeker_id会被忽略
        try{
            Profile profile = profileService.postProfile(profileDTO);
            ApplicationDTO a = new ApplicationDTO(profile.getId(), recruitTable_id,new Date());
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
    public Result getProfile(@RequestParam int id){
        return Result.success(profileService.getProfile(id));
    }

    /**
     * 获取某个招聘信息下所接收到的简历
     * @param id 招聘信息ID
     * @return 获取结果
     */
    @GetMapping("/tableProfile")
    public Result getTableProfile(@RequestParam int id) {
        try {
            return Result.success(profileService.getTableProfile(applicationService.getTableProfile(id)));
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 删除简历
     * @param profileId 简历ID
     * @return 删除结果
     */
    public boolean deleteProfile(Integer profileId){
        return applicationService.deleteApplicationById(profileId);
    }

}
