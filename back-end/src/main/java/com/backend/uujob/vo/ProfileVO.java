package com.backend.uujob.vo;

import lombok.Data;

@Data
public class ProfileVO {
    private Integer profileId;
    private String name;
    private Integer age;
    private String sex;
    private String email;
    private String education;
    private String phone;
    private String college;
    private String major;
    private String admissionDate;
    private String graduationDate;
    private String personalDescription;
    private String reward;
    private String expectedLocation;
    private String photoUrl;
    private String annexUrl;
    private Integer applicationId;
    private String applicationDate;
    private String reviewDate;
}
