package com.backend.uujob.mapper;

import com.backend.uujob.controller.dto.UserPasswordDTO;
import com.backend.uujob.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import  java.util.List;

/**
 * 用户功能接口
 *
 * @author Siyuson
 */
public interface UserMapper extends BaseMapper<User> {
    int updatePassword(@Param("userPasswordDTO") UserPasswordDTO userPasswordDTO);

}
