package com.backend.uujob.controller.dto;

import lombok.Data;

@Data
public class JobApplicationDTO {
    private String title;
    private String position;
    private String location;
    private String salary;
    private String applicationDate;
    private Integer status;
    private String reviewDate;
}
