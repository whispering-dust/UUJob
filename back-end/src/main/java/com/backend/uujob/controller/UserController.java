package com.backend.uujob.controller;

import com.backend.uujob.entity.User;
import com.backend.uujob.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mapleleaf
 * @date 2023年03月28日21:39
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * @description 通过id查找一个用户
     * @author mapleleaf
     * @date 2023/3/28 21:59
     */
    @GetMapping("/info")
    public String findOne(@RequestParam int id) {
            return userService.getNameById(id);
    }
}
