package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author mapleleaf
 * @date 2023年05月26日17:35
 */
@Data
public class Active {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    // 用户id
    private Integer userId;

    // 二级类目的id
    private Integer positionId;

    // 该用户对职位类别的点击量
    private Integer hits;
}
