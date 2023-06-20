package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Company;
import com.backend.uujob.entity.Post;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.mapper.CompanyMapper;
import com.backend.uujob.service.ICompanyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {
    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Company getCompanyByName(String name) {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        return getOne(queryWrapper);
    }
}
