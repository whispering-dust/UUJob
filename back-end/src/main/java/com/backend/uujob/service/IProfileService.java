package com.backend.uujob.service;

import com.backend.uujob.entity.Profile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IProfileService extends IService<Profile> {
    Profile getByUserId(int userId);
    List<Profile> getListByUserId(Integer userId);

    Profile copyGenerate(Integer profileId);
}
