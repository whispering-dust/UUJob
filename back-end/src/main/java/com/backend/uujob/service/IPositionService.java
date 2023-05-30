package com.backend.uujob.service;

import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.Message;
import com.backend.uujob.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年05月30日16:33
 */
public interface IPositionService extends IService<Position> {
    List<Position> getPositionList();

}
