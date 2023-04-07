package com.backend.uujob.entity;


public class Report {

  private Integer id;
  private Integer targetId;
  private Integer reporterId;
  private Integer type;
  private Integer status;
  private String content;
  private String feedback;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getTargetId() {
    return targetId;
  }

  public void setTargetId(Integer targetId) {
    this.targetId = targetId;
  }


  public Integer getReporterId() {
    return reporterId;
  }

  public void setReporterId(Integer reporterId) {
    this.reporterId = reporterId;
  }


  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }

}
