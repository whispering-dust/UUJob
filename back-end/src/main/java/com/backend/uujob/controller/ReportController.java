package com.backend.uujob.controller;

import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.Post;
import com.backend.uujob.entity.Report;
import com.backend.uujob.entity.VO.ReportVO;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.enums.ReportTypeEnum;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        List<Report> reportList = reportService.getListByStatus(ReportStatusEnum.REPORT_STATUS_PENDING);
        List<ReportVO> res = new ArrayList<>();
        for(Report r : reportList){
            ReportVO reportVO = new ReportVO();
            reportVO.setId(r.getId());
            reportVO.setReporterId(r.getReporterId());
            reportVO.setTargetId(r.getTargetId());
            reportVO.setContent(r.getContent());
            reportVO.setType(r.getType());

            reportVO.setReporterName(userService.getNameById(reportVO.getReporterId()));
            if(reportVO.getType() == ReportTypeEnum.REPORT_TYPE_POST.ordinal()){
                reportVO.setTargetContent(postService.getById(reportVO.getTargetId()).getTitle());
            }
            else if(reportVO.getType() == ReportTypeEnum.REPORT_TYPE_JOB.ordinal()){
                reportVO.setTargetContent(jobService.getById(reportVO.getTargetId()).getTitle());
            }
            else if(reportVO.getType() == ReportTypeEnum.REPORT_TYPE_COMMENT.ordinal()){
                reportVO.setTargetContent(commentService.getById(reportVO.getTargetId()).getContent());
            }

            res.add(reportVO);
        }

        return Result.success(res);
    }

    @PutMapping("/examinations")
    public Result handleReport(@RequestBody Report report){
        if(report.getStatus() == ReportStatusEnum.REPORT_STATUS_SUCCESS.ordinal()){
            if(report.getType() == ReportTypeEnum.REPORT_TYPE_POST.ordinal()){
                Post post = postService.getById(report.getTargetId());
                post.setStatus(CensorStatusEnum.CENSOR_STATUS_FAIL.ordinal());
                postService.updateById(post);
            }
            else if (report.getType() == ReportTypeEnum.REPORT_TYPE_JOB.ordinal()) {
                Job job = jobService.getById(report.getTargetId());
                job.setStatus(CensorStatusEnum.CENSOR_STATUS_FAIL.ordinal());
                jobService.updateById(job);
            }
            else if (report.getType() == ReportTypeEnum.REPORT_TYPE_COMMENT.ordinal()) {
                commentService.removeById(report.getTargetId());
            }
        }
        return Result.success(reportService.updateById(report));
    }
}
