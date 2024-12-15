package com.backend.uujob.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum StarTypeEnum implements IEnum<Integer> {
    STAR_TYPE_JOB(0), STAR_TYPE_POST(1),
    ;
    private Integer value;
    
    @Override
    public Integer getValue() {
        return value;
    }
    
    private StarTypeEnum(Integer value) {
        this.value = value;
    }
}
