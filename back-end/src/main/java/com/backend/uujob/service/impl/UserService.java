package com.backend.uujob.service.impl;

import com.backend.uujob.entity.User;
import com.backend.uujob.mapper.UserMapper;
import com.backend.uujob.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author mapleleaf
 * @date 2023年03月28日21:35
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public String getNameById(int id) {
        //在MyBatis-Plus中，通常通过QueryWrapper完成查询，其返回值类型为尖括号的类型
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return getOne(wrapper).getUserName();
    }

    @Override
    public User getByUserName(String userName) {
        //在MyBatis-Plus中，通常通过QueryWrapper完成查询，其返回值类型为尖括号的类型
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        return getOne(wrapper);
    }
}
