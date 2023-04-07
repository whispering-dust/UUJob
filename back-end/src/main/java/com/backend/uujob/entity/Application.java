package com.backend.uujob.entity;


import java.sql.Timestamp;

public class Application {

  private Integer profileId;
  private Integer jobId;
  private Timestamp date;
  private Integer status;


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


  public java.sql.Timestamp getDate() {
    return date;
  }

  public void setDate(java.sql.Timestamp date) {
    this.date = date;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

}
