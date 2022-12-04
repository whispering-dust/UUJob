package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName(value = "recruittable")
public class RecruitTable  implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer publisher_id;
    private String position;
    private String salary;
    private Date date;
    private short status;
    private String description;

    public RecruitTable(Integer id, Integer publisher_id, String position, String salary, Date date, String description) {
        this.id = id;
        this.publisher_id = publisher_id;
        this.position = position;
        this.salary = salary;
        this.date = date;
        this.description = description;
        this.status = 0;
    }

    public RecruitTable(Integer publisher_id, String position, String salary, Date date, short status, String description) {
        this.publisher_id = publisher_id;
        this.position = position;
        this.salary = salary;
        this.date = date;
        this.status = status;
        this.description = description;
    }

    public RecruitTable(Integer publisher_id){
        this.publisher_id = publisher_id;
        this.status = 0;
    }
}