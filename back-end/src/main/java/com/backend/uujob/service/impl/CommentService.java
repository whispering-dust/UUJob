package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.Post;
import com.backend.uujob.entity.VO.CommentVO;
import com.backend.uujob.enums.StatusEnum;
import com.backend.uujob.mapper.CommentMapper;
import com.backend.uujob.mapper.PostMapper;
import com.backend.uujob.service.ICommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月16日19:26
 */
@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<CommentVO> getCommentsByPostId(int postId) {
        return commentMapper.getCommentAndUser(postId);
    }
}
