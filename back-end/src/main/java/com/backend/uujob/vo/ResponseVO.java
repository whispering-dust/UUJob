package com.backend.uujob.vo;

import lombok.Data;

/**
 * @author mapleleaf
 * @date 2023年04月23日21:11
 */
@Data
public class ResponseVO {
    private Integer id;
    private Integer publisherId;
    private String publisherName;
    private String content;
    private String date;
    private Integer targetUserId;
    private String targetUserName;
}
