package com.backend.uujob.entity;

import lombok.Data;

/**
 * @author mapleleaf
 * @date 2023年05月26日17:35
 */

@Data
public class UserSimilarity {
    // 表id
    private Integer id;

    // 目前用户
    private Integer userId;

    //参考用户
    private Integer refUserId;

    //相似度
    private Double similarity;
}
