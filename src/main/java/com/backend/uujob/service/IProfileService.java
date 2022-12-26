package com.backend.uujob.service;

import com.backend.uujob.controller.dto.ProfileDTO;
import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Profile;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProfileService extends IService<Profile> {
    Profile postProfile(@RequestBody ProfileDTO profileDTO);
    Profile getProfile(int id);

    List<Profile> getTableProfile(List<Application> applicationList);
}
