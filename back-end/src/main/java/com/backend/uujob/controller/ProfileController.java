package com.backend.uujob.controller;

import com.backend.uujob.entity.Profile;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IProfileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Resource
    private IProfileService profileService;

    @PostMapping("")
    public Result addProfile(@RequestBody Profile profile) {
        profileService.save(profile);
        return Result.success(profile.getId());
    }

    @PutMapping("")
    public Result modifyProfile(@RequestBody Profile profile) {
        if(profileService.getById(profile.getId()) == null){
            return Result.error(Constants.CODE_500, "档案不存在");
        }
        profileService.updateById(profile);
        System.out.println(profile);
        return Result.success(profile.getId());
    }

    @DeleteMapping("")
    public Result deleteProfile(@RequestParam int id) {
        if(profileService.getById(id) == null){
            return Result.error(Constants.CODE_500, "档案不存在");
        }
        profileService.removeById(id);
        return Result.success();
    }
}
