package com.backend.uujob.service;

import com.backend.uujob.entity.Star;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IStarService extends IService<Star> {
    Star getByAll(Star star);
    List<Star> getByUserId(int userId);

}
