package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;

public class Message {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer senderId;
  private Integer receiverId;
  private String content;
  private Integer status;
  private Timestamp createdAt;


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


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public java.sql.Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(java.sql.Timestamp createdAt) {
    this.createdAt = createdAt;
  }

}
