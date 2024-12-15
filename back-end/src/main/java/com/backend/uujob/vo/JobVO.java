package com.backend.uujob.vo;

import com.backend.uujob.entity.Profile;
import lombok.Data;

@Data
public class JobVO {
    private Integer jobId;
    private String title;
    private String position;
    private String location;
    private String salary;
    private String applicationDate;
    private Integer status;
    private String reviewDate;
    private Profile profile;
}
