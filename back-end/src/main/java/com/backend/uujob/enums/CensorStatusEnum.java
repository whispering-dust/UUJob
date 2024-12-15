package com.backend.uujob.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum CensorStatusEnum implements IEnum<Integer> {
    CENSOR_STATUS_SUBMIT(0), CENSOR_STATUS_PASS(1), CENSOR_STATUS_TERMINATE(2), CENSOR_STATUS_FAIL(3),
    ;
    private Integer value;
    
    @Override
    public Integer getValue() {
        return value;
    }
    
    private CensorStatusEnum(Integer value) {
        this.value = value;
    }
}
