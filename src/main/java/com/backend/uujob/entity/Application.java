package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import java.util.Date;

@Data
@TableName(value = "application")
public class Application {

    @MppMultiId
    @TableField(value="resume_id")
    private int resume_id;
    @MppMultiId
    @TableField(value="job_id")
    private int job_id;
    private Date date;

    public Application(int resume_id, int job_id, Date date) {
        this.resume_id = resume_id;
        this.job_id = job_id;
        this.date = date;
    }
}
