package com.backend.uujob.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;

public class Profile {
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  private Integer seekerId;
  private String name;
  private Integer age;
  private String sex;
  private String email;
  private String education;
  private String phone;
  private String college;
  private String major;
  private Integer profileType;
  private Timestamp admissionDate;
  private Timestamp graduationDate;
  private String personalDescription;
  private String reward;
  private String expectedLocation;
  private String photoUrl;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProfileType() {
    return profileType;
  }

  public void setProfileType(Integer profileType) {
    this.profileType = profileType;
  }


  public Integer getSeekerId() {
    return seekerId;
  }

  public void setSeekerId(Integer seekerId) {
    this.seekerId = seekerId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getCollege() {
    return college;
  }

  public void setCollege(String college) {
    this.college = college;
  }


  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }


  public java.sql.Timestamp getAdmissionDate() {
    return admissionDate;
  }

  public void setAdmissionDate(java.sql.Timestamp admissionDate) {
    this.admissionDate = admissionDate;
  }


  public java.sql.Timestamp getGraduationDate() {
    return graduationDate;
  }

  public void setGraduationDate(java.sql.Timestamp graduationDate) {
    this.graduationDate = graduationDate;
  }


  public String getPersonalDescription() {
    return personalDescription;
  }

  public void setPersonalDescription(String personalDescription) {
    this.personalDescription = personalDescription;
  }


  public String getReward() {
    return reward;
  }

  public void setReward(String reward) {
    this.reward = reward;
  }


  public String getExpectedLocation() {
    return expectedLocation;
  }

  public void setExpectedLocation(String expectedLocation) {
    this.expectedLocation = expectedLocation;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

}
