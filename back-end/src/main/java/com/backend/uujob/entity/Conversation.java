package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;

public class Conversation {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer senderId;
  private Integer receiverId;
  private String lastMessage;
  private Timestamp lastMessageTime;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getSenderId() {
    return senderId;
  }

  public void setSenderId(Integer senderId) {
    this.senderId = senderId;
  }


  public Integer getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Integer receiverId) {
    this.receiverId = receiverId;
  }


  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }


  public java.sql.Timestamp getLastMessageTime() {
    return lastMessageTime;
  }

  public void setLastMessageTime(java.sql.Timestamp lastMessageTime) {
    this.lastMessageTime = lastMessageTime;
  }

}
