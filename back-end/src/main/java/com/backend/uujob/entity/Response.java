package com.backend.uujob.entity;


import java.sql.Timestamp;

public class Response {

  private Integer id;
  private String content;
  private Timestamp date;
  private Integer publiserId;
  private Integer commentId;
  private Integer targetUserId;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public java.sql.Timestamp getDate() {
    return date;
  }

  public void setDate(java.sql.Timestamp date) {
    this.date = date;
  }


  public Integer getPubliserId() {
    return publiserId;
  }

  public void setPubliserId(Integer publiserId) {
    this.publiserId = publiserId;
  }


  public Integer getCommentId() {
    return commentId;
  }

  public void setCommentId(Integer commentId) {
    this.commentId = commentId;
  }


  public Integer getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(Integer targetUserId) {
    this.targetUserId = targetUserId;
  }

}
