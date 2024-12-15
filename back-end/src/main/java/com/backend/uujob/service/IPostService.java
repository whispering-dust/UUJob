package com.backend.uujob.service;

import com.backend.uujob.dto.PostDTO;
import com.backend.uujob.dto.PostDetailDTO;
import com.backend.uujob.entity.Post;
import com.backend.uujob.enums.CensorStatusEnum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月15日21:11
 */
public interface IPostService extends IService<Post> {
    List<PostDTO> getPostList();

    PostDetailDTO getPostDetailById(int id);

    List<Post> getListByStatus(CensorStatusEnum censorStatusEnum);

    List<Post> getListByUserId(int userId);
}
