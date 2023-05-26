package com.backend.uujob.service;


import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.Post;
import com.backend.uujob.entity.VO.CommentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月16日19:26
 */
public interface ICommentService extends IService<Comment> {
    List<CommentVO> getCommentsByPostId(int postId);

    List<Comment> getListByUserId(int userId);
}
