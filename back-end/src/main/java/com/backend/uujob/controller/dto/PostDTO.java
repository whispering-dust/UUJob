package com.backend.uujob.controller.dto;

import com.backend.uujob.entity.Post;
import lombok.Data;
import java.sql.Timestamp;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

/**
 * @author mapleleaf
 * @date 2023年04月19日14:54
 */
@Data
public class PostDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer publisherId;
    private String date;
    private Integer status;
    private Integer type;
    public PostDTO(Post p){
        this.id=p.getId();
        this.title=p.getTitle();
        this.content=p.getContent();
        this.status=p.getStatus();
        this.date=timeTransfer(p.getDate());
        this.type=p.getType();
        this.publisherId = p.getPublisherId();
    }
}
