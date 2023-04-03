package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author mapleleaf
 * @date 2023年03月28日21:18
 */
@Data
@TableName(value = "user")
public class User {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String account;
    @JsonIgnore
    private String password;
    private String name;
    private String phone;
    private short role;
    private Integer companyId;
    private String position;
}
