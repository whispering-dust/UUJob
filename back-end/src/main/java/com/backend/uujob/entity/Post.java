package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Post {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private String title;
  private String content;
  private Integer publisherId;
  private Timestamp date;
  private Integer status;
  private Integer type;
}
