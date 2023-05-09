package com.backend.uujob.controller;

import com.backend.uujob.controller.dto.PostDetailDTO;
import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.Post;
import com.backend.uujob.entity.VO.CommentVO;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.ICommentService;
import com.backend.uujob.service.IPostService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * @author mapleleaf
 * @date 2023年04月15日21:07
 */
@RestController
@RequestMapping("/posts")
public class PostController {
    @Resource
    private IPostService postService;
    @Resource
    private ICommentService commentService;

    @GetMapping("/basis")
    public Result getPosts() {
        return Result.success(postService.getPostList());
    }

    @GetMapping("/detail")
    public Result getPostDetail(@RequestParam int id) {
        //此时尚未添加评论信息
        PostDetailDTO targetPost = postService.getPostDetailById(id);

        if(targetPost == null){
            return Result.error(Constants.CODE_500, "帖子不存在");
        }

        //装配评论信息
        List<CommentVO> commentVOList = commentService.getCommentsByPostId(id);
        targetPost.setCommentList(commentVOList);

        return Result.success(targetPost);
    }

    @PostMapping("")
    public Result addPost(@RequestBody Post post){
        post.setStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT.ordinal());
        if(postService.save(post)){
            return Result.success(post.getId());
        }
        else{
            return Result.error();
        }
    }

    @PutMapping("")
    public Result modifyPost(@RequestBody Post post){
        if(postService.updateById(post)){
            return Result.success(post.getId());
        }
        return Result.error();
    }

    @PostMapping("/comments")
    public Result addCommentForPost(@RequestBody Comment comment){
        Long timeL = System.currentTimeMillis();
        Timestamp time=new Timestamp(timeL);
        comment.setDate(time);
        if(commentService.save(comment)){
            return Result.success(comment.getId());
        }
        else{
            return Result.error();
        }
    }

    @GetMapping("/unaudited")
    public Result getPostUnaudited() {
        return Result.success(postService.getListByStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT));
    }
}
