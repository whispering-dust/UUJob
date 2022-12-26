package com.backend.uujob.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.backend.uujob.common.Constants;
import com.backend.uujob.common.Result;
import com.backend.uujob.controller.dto.LoginDTO;
import com.backend.uujob.controller.dto.UserDTO;
import com.backend.uujob.entity.User;
import com.backend.uujob.exception.ServiceException;
import com.backend.uujob.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 用户登录
     * @param loginDTO 传入用户账号、密码
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    /**
     * 修改用户个人信息
     * @param userDTO 修改后的用户信息
     * @return 布尔类型，判断修改是否成功
     */
    @PostMapping
    private boolean save(@RequestBody UserDTO userDTO){
        User one=new User();
        BeanUtil.copyProperties(one,userDTO,true);
        return userService.saveOrUpdate(one);
    }
    //测试用的Get接口

    /**
     * 测试查询所有信息
     *
     * @return 返回所有用户信息
     */
    @GetMapping
    public List<User> findAll(){
        return userService.list();
    }

    /**
     * 用户注册
     * @param userDTO 用户用于注册的json信息
     * @return 返回注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String account = userDTO.getAccount();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(account) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    /**
     * 用户个人信息接口
     *
     * @param id 用户账号
     * @return 返回用户个人信息
     */
    @GetMapping("/info")
    public Result findOne(@RequestParam int id) {
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            return Result.success(userService.getOne(queryWrapper));
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }
    /**
     * 删除数据
     *
     * @param id 要删除用户id
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

}
