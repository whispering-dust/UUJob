package com.backend.uujob.controller.dto;

import lombok.Data;

@Data
public class ResumeDTO {
    private Integer id;
    private String name;
    private short age;
    private String sex;
    private String email;
    private Integer seeker_id;
    private String edu;
    private String description;
}
