package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Report {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer targetId;
  private Integer reporterId;
  private Integer type;
  private Integer status;
  private String content;
  private String feedback;
}
