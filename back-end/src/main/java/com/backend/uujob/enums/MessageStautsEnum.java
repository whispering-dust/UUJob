package com.backend.uujob.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum MessageStautsEnum implements IEnum<Integer> {
    MESSAGE_STAUTS_UNREAD(0), MESSAGE_STAUTS_READ(1);
    private Integer value;
    
    @Override
    public Integer getValue() {
        return value;
    }
    
    private MessageStautsEnum(Integer value) {
        this.value = value;
    }
}
