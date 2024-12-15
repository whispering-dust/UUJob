package com.backend.uujob.vo;

import lombok.Data;

@Data
public class StarVO {
    private Integer id;
    private Integer starType;
    private Integer targetId;
    private String title;
}
