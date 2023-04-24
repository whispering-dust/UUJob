package com.backend.uujob.entity.VO;

import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Job;
import com.backend.uujob.enums.ApplStatusEnum;
import lombok.Data;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

@Data
public class JobVO {
    private String title;
    private String position;
    private String location;
    private String salary;
    private String applicationDate;
    private Integer status;
    private String reviewDate;

}
