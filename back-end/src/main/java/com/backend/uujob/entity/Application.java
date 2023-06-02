package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Timestamp;

public class Application {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer profileId;
  private Integer jobId;
  private String annexUrl;
  private Timestamp applicationDate;
  private Integer status;
  private Timestamp reviewDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProfileId() {
    return profileId;
  }

  public void setProfileId(Integer profileId) {
    this.profileId = profileId;
  }


  public Integer getJobId() {
    return jobId;
  }

  public void setJobId(Integer jobId) {
    this.jobId = jobId;
  }

  public String getAnnexUrl() {
    return annexUrl;
  }

  public void setAnnexUrl(String annexUrl) {
    this.annexUrl = annexUrl;
  }


  public java.sql.Timestamp getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(java.sql.Timestamp applicationDate) {
    this.applicationDate = applicationDate;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public java.sql.Timestamp getReviewDate() {
    return reviewDate;
  }

  public void setReviewDate(java.sql.Timestamp reviewDate) {
    this.reviewDate = reviewDate;
  }

}
