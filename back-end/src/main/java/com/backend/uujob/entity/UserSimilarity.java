package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author mapleleaf
 * @date 2023年05月26日17:35
 */

@Data
public class UserSimilarity {
    // 表id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    // 目前用户
    private Integer userId;

    //参考用户
    private Integer refUserId;

    //相似度
    private Double similarity;
}
