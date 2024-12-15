package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private String phone;
    private Integer role;
    private Integer companyId;
    private String position;
    private Integer status;
    private Timestamp bannedEndTime;
}
