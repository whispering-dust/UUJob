package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName(value = "job")
public class Job implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer publisher_id;
    private String title;
    private String position;
    private String salary;
    private Date date;
    private short status;
    private String description;

    public Job(Integer publisher_id){
        this.id = 0;//自动递增而不指定
        this.publisher_id = publisher_id;
        this.status = 0;
    }
}
