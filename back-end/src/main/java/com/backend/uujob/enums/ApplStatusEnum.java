package com.backend.uujob.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

public enum ApplStatusEnum implements IEnum {
    APPL_STATUS_SUBMIT(0), APPL_STATUS_PASS(1), APPL_STATUS_REJECTED(2);
    
    private Integer value;
    
    @Override
    public Integer getValue() {
        return value;
    }
    
    private ApplStatusEnum(Integer value) {
        this.value = value;
    }
}
