package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import java.sql.Timestamp;

public class Application {
  @MppMultiId
  @TableField(value="profile_id")
  private Integer profileId;
  @MppMultiId
  @TableField(value="job_id")
  private Integer jobId;
  private Timestamp applicationDate;
  private Integer status;
  private Timestamp reviewDate;


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
