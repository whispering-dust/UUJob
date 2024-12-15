package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Report;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.mapper.ReportMapper;
import com.backend.uujob.service.IReportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportService extends ServiceImpl<ReportMapper, Report> implements IReportService {
    @Override
    public List<Report> getListByStatus(ReportStatusEnum reportStatusEnum) {
        QueryWrapper<Report> rQueryWrapper = new QueryWrapper<>();
        rQueryWrapper
                .eq("status", reportStatusEnum.getValue());  //满足状态的举报才予以显示
        
        return list(rQueryWrapper);
    }
    
    @Override
    public List<Report> getListByTargetIdAndType(Integer reportTypeEnum, Integer targetId) {
        QueryWrapper<Report> rQueryWrapper = new QueryWrapper<>();
        rQueryWrapper
                .eq("target_id", targetId)
                .eq("type", reportTypeEnum);
        
        return list(rQueryWrapper);
    }
}
