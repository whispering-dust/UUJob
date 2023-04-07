package com.backend.uujob.entity;


import java.sql.Timestamp;

public class Job {

  private Integer id;
  private String title;
  private String position;
  private String location;
  private String salary;
  private Timestamp date;
  private String description;
  private Integer publisherId;
  private Integer status;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public String getSalary() {
    return salary;
  }

  public void setSalary(String salary) {
    this.salary = salary;
  }


  public java.sql.Timestamp getDate() {
    return date;
  }

  public void setDate(java.sql.Timestamp date) {
    this.date = date;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public Integer getPublisherId() {
    return publisherId;
  }

  public void setPublisherId(Integer publisherId) {
    this.publisherId = publisherId;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

}
