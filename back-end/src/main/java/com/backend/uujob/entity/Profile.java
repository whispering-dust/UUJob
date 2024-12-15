package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Profile {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer seekerId;
    private String name;
    private Integer age;
    private String sex;
    private String email;
    private String education;
    private String phone;
    private String college;
    private String major;
    private Integer profileType;
    private Timestamp admissionDate;
    private Timestamp graduationDate;
    private String personalDescription;
    private String reward;
    private String expectedLocation;
    private String photoUrl;
}
