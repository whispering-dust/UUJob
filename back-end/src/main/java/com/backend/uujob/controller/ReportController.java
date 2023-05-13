package com.backend.uujob.controller;

import com.backend.uujob.entity.Report;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.enums.ReportTypeEnum;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IReportService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Resource
    private IReportService reportService;

    @PostMapping("/jobs")
    public Result reportJob(@RequestBody Report report){
        report.setType(ReportTypeEnum.REPORT_TYPE_JOB.ordinal());
        report.setStatus(ReportStatusEnum.REPORT_STATUS_PENDING.ordinal());
        reportService.save(report);
        return Result.success(report.getId());
    }

    @PostMapping("/posts")
    public Result reportPost(@RequestBody Report report){
        report.setType(ReportTypeEnum.REPORT_TYPE_POST.ordinal());
        report.setStatus(ReportStatusEnum.REPORT_STATUS_PENDING.ordinal());
        reportService.save(report);
        return Result.success(report.getId());
    }

    @PostMapping("/comments")
    public Result reportComment(@RequestBody Report report){
        report.setType(ReportTypeEnum.REPORT_TYPE_COMMENT.ordinal());
        report.setStatus(ReportStatusEnum.REPORT_STATUS_PENDING.ordinal());
        reportService.save(report);
        return Result.success(report.getId());
    }

    @GetMapping("/unaudited")
    public Result getReportUnaudited(){
        return Result.success(reportService.getListByStatus(ReportStatusEnum.REPORT_STATUS_PENDING));
    }

    @PutMapping("/examinations")
    public Result handleReport(@RequestBody Report report){
        return Result.success(reportService.updateById(report));
    }
}
