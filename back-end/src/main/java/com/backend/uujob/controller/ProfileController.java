package com.backend.uujob.controller;

import com.backend.uujob.entity.Profile;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IProfileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.backend.uujob.utils.FTPUtils.uploadFolder;

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
        if (profileService.getById(profile.getId()) == null) {
            return Result.error(Constants.CODE_500, "档案不存在");
        }
        profileService.updateById(profile);
        System.out.println(profile);
        return Result.success(profile.getId());
    }
    
    @DeleteMapping("")
    public Result deleteProfile(@RequestParam int id) {
        if (profileService.getById(id) == null) {
            return Result.error(Constants.CODE_500, "档案不存在");
        }
        profileService.removeById(id);
        return Result.success();
    }
    
    @PostMapping("/upload-files")
    public Result addPhotoForProfile(
            @RequestParam("profileId") Integer profileId,
            @RequestParam("photo") MultipartFile photo
    ) {
        Profile profile = profileService.getById(profileId);
        if (profile == null) {
            return Result.error(Constants.CODE_400, "该简历不存在");
        }
        
        String url = uploadFolder(photo);
        if (url != null) {
            profile.setPhotoUrl(url);
            profileService.updateById(profile);
            return Result.success(url);
        }
        return Result.error(Constants.CODE_500, "文件上传失败");
    }
}
