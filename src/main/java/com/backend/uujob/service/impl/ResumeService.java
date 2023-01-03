package com.backend.uujob.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.backend.uujob.common.Constants;
import com.backend.uujob.common.RoleEnum;
import com.backend.uujob.controller.dto.ResumeDTO;
import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Resume;
import com.backend.uujob.exception.ServiceException;
import com.backend.uujob.mapper.ResumeMapper;
import com.backend.uujob.service.IResumeService;
import com.backend.uujob.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ResumeService extends ServiceImpl<ResumeMapper, Resume> implements IResumeService {

    private final IUserService userService;
    @Autowired
    public ResumeService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Resume postResume(ResumeDTO resumeDTO) {
        //StpUtil.getLoginIdAsInt();
        if (RoleEnum.ROLE_SEEKER.ordinal() == userService.getRoleById(resumeDTO.getSeeker_id())) { //检查用户是否有发布权限，后续可能用satoken的函数进行替代
            //保存profile
            resumeDTO.setName(userService.getNameById(resumeDTO.getSeeker_id()));
            Resume resume = new Resume();
            BeanUtil.copyProperties(resumeDTO, resume,true);
            resume.setId(0);//自动递增而不指定
            save(resume);
            return resume;
        } else {
            throw new ServiceException(Constants.CODE_401, "用户权限不足");
        }
    }

    @Override
    public Resume getResume(int id) {
        QueryWrapper<Resume> wrapper = new QueryWrapper<>();
        wrapper.eq("seeker_id",id);
        return getOne(wrapper);
    }
    @Override
    public List<Resume> getJobResume(List<Application> applicationList){
        List<Resume> resumeList = new ArrayList<>();
        for (Application each:applicationList) {
            QueryWrapper<Resume> wrapper = new QueryWrapper<>();
            wrapper.eq("id",each.getResume_id());
            resumeList.add(getOne(wrapper));
        }
        return resumeList;
    }
}
