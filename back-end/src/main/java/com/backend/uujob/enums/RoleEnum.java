package com.backend.uujob.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum RoleEnum implements IEnum<Integer> {
    ROLE_SEEKER(0), ROLE_RECRUITER(1), ROLE_ADMIN(2),
    ;
    
    private Integer value;
    
    @Override
    public Integer getValue() {
        return value;
    }
    
    private RoleEnum(Integer value) {
        this.value = value;
    }
}
