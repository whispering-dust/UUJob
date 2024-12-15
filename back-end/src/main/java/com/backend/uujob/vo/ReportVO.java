package com.backend.uujob.vo;

import lombok.Data;

@Data
public class ReportVO {
    private Integer id;
    private Integer targetId;
    private String targetContent;
    private Integer reporterId;
    private String reporterName;
    private String content;
    private Integer type;
}
