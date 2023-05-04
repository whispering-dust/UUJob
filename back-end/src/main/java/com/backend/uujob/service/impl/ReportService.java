package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Report;
import com.backend.uujob.mapper.ReportMapper;
import com.backend.uujob.service.IReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReportService extends ServiceImpl<ReportMapper, Report> implements IReportService {
    @Resource
    private ReportMapper reportMapper;
}
