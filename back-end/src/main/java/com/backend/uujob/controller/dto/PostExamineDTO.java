package com.backend.uujob.controller.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostExamineDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer publisherId;
    private String userName;
    private Integer type;
}
