package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Application {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer profileId;
  private Integer jobId;
  private String annexUrl;
  private Timestamp applicationDate;
  private Integer status;
  private Timestamp reviewDate;
}
