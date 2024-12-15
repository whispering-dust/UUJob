package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Job {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private String title;
  private String position;
  private Integer positionId;
  private String location;
  private String salary;
  private String education;
  private String companyName;
  private Timestamp date;
  private String description;
  private String templateUrl;
  private Integer publisherId;
  private Integer status;
}
