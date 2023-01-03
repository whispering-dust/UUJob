package com.backend.uujob.entity;

import com.backend.uujob.controller.dto.UserDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "user")
public class User implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String account;
    @JsonIgnore
    private String password;
    private String name;
    private String phone;
    private short role;
    private String company;
    private String position;
    private int company_id;

    public User() {
        ;
    }

    public User(Integer id, String account, String password, String name, String phone, short role, String company, String position, int company_id) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.company = company;
        this.position = position;
        this.company_id = company_id;
    }
    
}
