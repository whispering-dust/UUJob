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
    private String position;
    private int companyId;

    public User() {
        ;
    }

    public User(Integer id, String account, String password, String name, String phone, short role,String position, int companyId) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.position = position;
        this.companyId = companyId;
    }
    
}
