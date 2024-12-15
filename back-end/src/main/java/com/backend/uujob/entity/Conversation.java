package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Conversation {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer senderId;
  private Integer receiverId;
  private String lastMessage;
  private Timestamp lastMessageTime;
}
