package com.backend.uujob.service;

import com.backend.uujob.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {
    String getNameById(int id);
}
