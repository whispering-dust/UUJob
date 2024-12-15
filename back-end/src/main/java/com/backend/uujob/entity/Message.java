package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Message {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer senderId;
  private Integer receiverId;
  private String content;
  private Integer status;
  private Timestamp createdAt;
  private Integer conversationId;
}
