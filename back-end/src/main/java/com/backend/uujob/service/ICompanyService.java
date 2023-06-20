package com.backend.uujob.service;

import com.backend.uujob.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ICompanyService extends IService<Company> {
    public Company getCompanyByName(String name);
}
