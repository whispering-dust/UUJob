package com.backend.uujob.entity.VO;

import lombok.Data;

@Data
public class StarVO {
    private Integer id;
    private Integer starType;
    private Integer targetId;
    private String title;
}
