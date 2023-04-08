package com.backend.uujob.service;

import com.backend.uujob.entity.Profile;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IProfileService extends IService<Profile> {
    Profile getByUserId(int userId);
}
