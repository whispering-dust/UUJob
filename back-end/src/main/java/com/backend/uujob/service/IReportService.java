package com.backend.uujob.service;

import com.backend.uujob.entity.Report;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.enums.ReportTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IReportService extends IService<Report> {
    List<Report> getListByStatus(ReportStatusEnum reportStatusEnum);

    List<Report> getListByTargetIdAndType(Integer reportTypeEnum, Integer targetId);
}
