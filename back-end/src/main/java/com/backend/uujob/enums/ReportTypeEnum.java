package com.backend.uujob.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ReportTypeEnum implements IEnum<Integer> {
    REPORT_TYPE_JOB(0), REPORT_TYPE_POST(1), REPORT_TYPE_COMMENT(2);
    
    private Integer value;
    
    @Override
    public Integer getValue() {
        return value;
    }
    
    private ReportTypeEnum(Integer value) {
        this.value = value;
    }
}
