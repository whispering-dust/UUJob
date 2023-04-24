package com.backend.uujob.entity.VO;

import lombok.Data;

/**
 * @author mapleleaf
 * @date 2023年04月16日20:22
 */
@Data
public class CommentVO {
    private Integer id;
    private String content;
    private String date;
    private Integer publisherId;
    private String userName;
    private Integer responseNum;
}
