package com.backend.uujob.controller;

import com.backend.uujob.vo.ReportVO;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.Post;
import com.backend.uujob.entity.Report;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.enums.ReportTypeEnum;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Resource
    private IReportService reportService;
    @Resource
    private IUserService userService;
    @Resource
    private IJobService jobService;
    @Resource
    private IPostService postService;
    @Resource
    private ICommentService commentService;
    
    
    @PostMapping("/jobs")
    public Result reportJob(@RequestBody Report report) {
        report.setType(ReportTypeEnum.REPORT_TYPE_JOB.getValue());
        report.setStatus(ReportStatusEnum.REPORT_STATUS_PENDING.getValue());
        reportService.save(report);
        return Result.success(report.getId());
    }
    
    @PostMapping("/posts")
    public Result reportPost(@RequestBody Report report) {
        report.setType(ReportTypeEnum.REPORT_TYPE_POST.getValue());
        report.setStatus(ReportStatusEnum.REPORT_STATUS_PENDING.getValue());
        reportService.save(report);
        return Result.success(report.getId());
    }
    
    @PostMapping("/comments")
    public Result reportComment(@RequestBody Report report) {
        report.setType(ReportTypeEnum.REPORT_TYPE_COMMENT.getValue());
        report.setStatus(ReportStatusEnum.REPORT_STATUS_PENDING.getValue());
        reportService.save(report);
        return Result.success(report.getId());
    }
    
    @GetMapping("/unaudited")
    public Result getReportUnaudited() {
        List<Report> reportList = reportService.getListByStatus(ReportStatusEnum.REPORT_STATUS_PENDING);
        List<ReportVO> res = new ArrayList<>();
        for (Report r : reportList) {
            ReportVO reportVO = new ReportVO();
            reportVO.setId(r.getId());
            reportVO.setReporterId(r.getReporterId());
            reportVO.setTargetId(r.getTargetId());
            reportVO.setContent(r.getContent());
            reportVO.setType(r.getType());
            
            reportVO.setReporterName(userService.getNameById(reportVO.getReporterId()));
            if (Objects.equals(reportVO.getType(), ReportTypeEnum.REPORT_TYPE_POST.getValue())) {
                reportVO.setTargetContent(postService.getById(reportVO.getTargetId()).getTitle());
            } else if (Objects.equals(reportVO.getType(), ReportTypeEnum.REPORT_TYPE_JOB.getValue())) {
                reportVO.setTargetContent(jobService.getById(reportVO.getTargetId()).getTitle());
            } else if (Objects.equals(reportVO.getType(), ReportTypeEnum.REPORT_TYPE_COMMENT.getValue())) {
                reportVO.setTargetContent(commentService.getById(reportVO.getTargetId()).getContent());
            }
            
            res.add(reportVO);
        }
        
        return Result.success(res);
    }
    
    @PutMapping("/examinations")
    public Result handleReport(@RequestBody Report report) {
        Report tmp = reportService.getById(report.getId());
        tmp.setStatus(report.getStatus());
        
        if (Objects.equals(tmp.getStatus(), ReportStatusEnum.REPORT_STATUS_SUCCESS.getValue())) {  //如果举报被受理，则将被举报的目标改为封禁或直接删除
            if (Objects.equals(tmp.getType(), ReportTypeEnum.REPORT_TYPE_POST.getValue())) {
                Post post = postService.getById(tmp.getTargetId());
                post.setStatus(CensorStatusEnum.CENSOR_STATUS_FAIL.getValue());
                postService.updateById(post);
            } else if (Objects.equals(tmp.getType(), ReportTypeEnum.REPORT_TYPE_JOB.getValue())) {
                Job job = jobService.getById(tmp.getTargetId());
                job.setStatus(CensorStatusEnum.CENSOR_STATUS_FAIL.getValue());
                jobService.updateById(job);
            } else if (Objects.equals(tmp.getType(), ReportTypeEnum.REPORT_TYPE_COMMENT.getValue())) {
                commentService.removeById(tmp.getTargetId());
            }
            
            List<Report> reportList = reportService.getListByTargetIdAndType(tmp.getType(), tmp.getTargetId());  //同时将举报相同目标的举报一同改为举报成功状态
            for (Report r : reportList) {
                r.setStatus(ReportStatusEnum.REPORT_STATUS_SUCCESS.getValue());
                reportService.updateById(r);
            }
        }
        return Result.success(reportService.updateById(tmp));
    }
}
