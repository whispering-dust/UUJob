package com.backend.uujob.service;

import com.backend.uujob.controller.dto.PostDetailDTO;
import com.backend.uujob.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月15日21:11
 */
public interface IPostService extends IService<Post> {
    List<Post> getPostList();

    PostDetailDTO getPostDetailById(int id);
}
