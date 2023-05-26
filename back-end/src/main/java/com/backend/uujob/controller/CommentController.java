package com.backend.uujob.controller;

import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.Response;
import com.backend.uujob.entity.VO.CommentVO;
import com.backend.uujob.entity.VO.ResponseVO;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.ICommentService;
import com.backend.uujob.service.IResponseService;
import com.backend.uujob.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

/**
 * @author mapleleaf
 * @date 2023年04月23日20:38
 */
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Resource
    private ICommentService commentService;

    @Resource
    private IResponseService responseService;

    @Resource
    private IUserService userService;

    @GetMapping("")
    public Result getComment(@RequestParam int id){
        Comment comment = commentService.getById(id);

        CommentVO commentVO = new CommentVO();  //设置commentVO的信息
        commentVO.setId(id);
        commentVO.setPublisherId(comment.getPublisherId());
        commentVO.setUserName(userService.getNameById(comment.getPublisherId()));
        commentVO.setDate(timeTransfer(comment.getDate()));
        commentVO.setContent(comment.getContent());

        return Result.success(commentVO);
    }

    @GetMapping("/responses")
    public Result getResponsesOfComment(@RequestParam int id){
        List<ResponseVO> targetList = responseService.getResponsesByCommentId(id);
        if(targetList.isEmpty()){
            return Result.error();
        }
        return Result.success(targetList);
    }

    @PostMapping("/responses")
    public Result addResponseForComment(@RequestBody Response response){
        Long timeL = System.currentTimeMillis();
        Timestamp time = new Timestamp(timeL);
        response.setDate(time);
        if(responseService.save(response)){
            return Result.success(response.getId());
        }
        else{
            return Result.error();
        }
    }

    @DeleteMapping("/responses")
    public Result deleteResponsesOfComment(@RequestParam int responseId){
        if(responseService.removeById(responseId)){
            return Result.success();
        }
        return Result.error();
    }

    @DeleteMapping("")
    public Result deleteComment(@RequestParam int commentId){
        List<ResponseVO> responseVOList = responseService.getResponsesByCommentId(commentId);
        if(!responseVOList.isEmpty()){  //若评论存在回复，则首先删除针对该评论的回复
            for(ResponseVO r : responseVOList){
                responseService.removeById(r.getId());
            }
        }

        if(commentService.removeById(commentId)){
            return Result.success();
        }
        return Result.error();
    }
}
