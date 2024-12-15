package com.backend.uujob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author mapleleaf
 * @date 2023年05月24日16:31
 */

@Data
public class Position {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private String positionName;
}
