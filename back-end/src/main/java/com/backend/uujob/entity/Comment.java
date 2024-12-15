package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private String content;
  private Timestamp date;
  private Integer publisherId;
  private Integer postId;
}
