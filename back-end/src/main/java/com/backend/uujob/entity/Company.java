package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Company {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private String name;
  private String address;
  private String description;
}
