package com.backend.uujob.service.impl;


import com.backend.uujob.vo.CommentVO;
import com.backend.uujob.entity.Comment;
import com.backend.uujob.mapper.CommentMapper;
import com.backend.uujob.service.ICommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月16日19:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;
    
    @Override
    public List<CommentVO> getCommentsByPostId(int postId) {
        return commentMapper.getCommentAndUser(postId);
    }
    
    @Override
    public List<Comment> getListByUserId(int userId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publisher_id", userId)  //寻找该用户发布的评论
                .orderByDesc("date");  //按照帖子创建时间进行降序排序后返回，方便前端显示
        return list(queryWrapper);
    }
}
