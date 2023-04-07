package com.backend.uujob.entity;


import java.sql.Timestamp;

public class User {

  private Integer id;
  private String userName;
  private String password;
  private String phone;
  private Integer role;
  private Integer companyId;
  private String position;
  private Integer status;
  private Timestamp bannedEndTime;
  private String cookie;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = role;
  }


  public Integer getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Integer companyId) {
    this.companyId = companyId;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public java.sql.Timestamp getBannedEndTime() {
    return bannedEndTime;
  }

  public void setBannedEndTime(java.sql.Timestamp bannedEndTime) {
    this.bannedEndTime = bannedEndTime;
  }


  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

}
