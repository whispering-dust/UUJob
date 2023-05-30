package com.backend.uujob.service;

import com.backend.uujob.entity.Active;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年05月30日17:46
 */
public interface IActiveService extends IService<Active> {
    /**
     * 查询出所有的用户行为
     * @return 返回用户的行为数据
     */
    List<Active> listAllUserActive();

    /**
     * 保存用户的浏览记录，如果用户的浏览记录存在则更新，不存在则添加
     * @param userActive 用户的行为数据
     * @return true表示更新成功，false表示更新失败
     */
    int saveUserActive(Active userActive);
}
