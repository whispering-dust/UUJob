package com.backend.uujob.dto;

import lombok.Data;

@Data
public class JobExamineDTO {
    private Integer id;
    private String title;
    private String position;
    private String location;
    private String salary;
    private String education;
    private String description;
    private Integer publisherId;
    private String userName;
    private String companyName;
}
