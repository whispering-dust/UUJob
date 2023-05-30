package com.backend.uujob.controller;

import com.backend.uujob.controller.dto.JobExamineDTO;
import com.backend.uujob.controller.dto.PostDTO;
import com.backend.uujob.controller.dto.PostDetailDTO;
import com.backend.uujob.controller.dto.PostExamineDTO;
import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.Post;
import com.backend.uujob.entity.VO.CommentVO;
import com.backend.uujob.entity.VO.ResponseVO;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.enums.ReportStatusEnum;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.ICommentService;
import com.backend.uujob.service.IPostService;
import com.backend.uujob.service.IResponseService;
import com.backend.uujob.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    @Resource
    private IResponseService responseService;
    @Resource
    private IUserService userService;

    @GetMapping("/basis")
    public Result getPosts() {
        List<PostDTO> list = postService.getPostList();
        if(!list.isEmpty()){
            return Result.success(list);
        }
        return Result.error(Constants.CODE_500,"帖子数据获取失败");
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
            return Result.error(Constants.CODE_500,"数据保存失败");
        }
    }

    @PutMapping("")
    public Result modifyPost(@RequestBody Post post){
        if(postService.updateById(post)){
            return Result.success(post.getId());
        }
        return Result.error(Constants.CODE_500,"数据更新失败");
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
            return Result.error(Constants.CODE_500,"数据保存失败");
        }
    }

    @GetMapping("/unaudited")
    public Result getPostUnaudited() {
        List<Post> postList = postService.getListByStatus(CensorStatusEnum.CENSOR_STATUS_SUBMIT);
        List<PostExamineDTO> postExamineDTOList = new ArrayList<>();
        for(Post p : postList){
            PostExamineDTO postExamineDTO = new PostExamineDTO();
            postExamineDTO.setId(p.getId());
            postExamineDTO.setTitle(p.getTitle());
            postExamineDTO.setContent(p.getContent());
            postExamineDTO.setType(p.getType());
            postExamineDTO.setPublisherId(p.getPublisherId());
            postExamineDTO.setUserName(userService.getNameById(p.getPublisherId()));

            postExamineDTOList.add(postExamineDTO);
        }

        return Result.success(postExamineDTOList);
    }

    @PutMapping("/examinations")
    public Result examinePost(@RequestBody Post post){
        post.setDate(new java.sql.Timestamp(System.currentTimeMillis()));  //获取当前时间作为帖子的发布时间
        if(postService.updateById(post)){
            return Result.success();
        }
        return Result.error(Constants.CODE_500,"审查数据修改失败");
    }

    @DeleteMapping("")
    public Result deletePost(@RequestParam int postId){
        List<CommentVO> comments = commentService.getCommentsByPostId(postId);  //找到帖子所有评论
        if(!comments.isEmpty()){
            for(CommentVO c : comments){  //对每个评论进行操作
                List<ResponseVO> responseVOList = responseService.getResponsesByCommentId(c.getId());
                if(!responseVOList.isEmpty()){  //若评论存在回复，则先找到所有回复并删除
                    for(ResponseVO r : responseVOList){
                        responseService.removeById(r.getId());
                    }
                }
                commentService.removeById(c.getId());  //随后删除评论本身
            }
        }

        if(postService.removeById(postId)){
            return Result.success();
        }
        return Result.error(Constants.CODE_500,"数据库更改失败");
    }
}
