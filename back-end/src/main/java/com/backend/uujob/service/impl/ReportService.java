package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Post;
import com.backend.uujob.entity.Report;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.mapper.ReportMapper;
import com.backend.uujob.service.IReportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService extends ServiceImpl<ReportMapper, Report> implements IReportService {
    @Resource
    private ReportMapper reportMapper;
    @Override
    public List<Report> getListByStatus(ReportStatusEnum reportStatusEnum){
        QueryWrapper<Report> rQueryWrapper = new QueryWrapper<>();
        rQueryWrapper
                .eq("status", reportStatusEnum.ordinal());  //满足状态的举报才予以显示

        return list(rQueryWrapper);
    }
}
