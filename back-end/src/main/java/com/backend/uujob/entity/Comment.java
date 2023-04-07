package com.backend.uujob.entity;


import java.sql.Timestamp;

public class Comment {

  private Integer id;
  private String content;
  private Timestamp date;
  private Integer publiserId;
  private Integer postId;


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


  public Integer getPostId() {
    return postId;
  }

  public void setPostId(Integer postId) {
    this.postId = postId;
  }

}
