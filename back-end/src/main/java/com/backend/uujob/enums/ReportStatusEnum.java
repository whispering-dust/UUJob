package com.backend.uujob.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ReportStatusEnum implements IEnum<Integer> {
    REPORT_STATUS_PENDING(0), REPORT_STATUS_SUCCESS(1), REPORT_STATUS_FAIL(2);
    private Integer value;
    
    @Override
    public Integer getValue() {
        return value;
    }
    private ReportStatusEnum(Integer value) {
        this.value = value;
    }
}
