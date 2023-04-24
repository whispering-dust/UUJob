package com.backend.uujob.controller;

import com.backend.uujob.entity.Response;
import com.backend.uujob.entity.VO.ResponseVO;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.ICommentService;
import com.backend.uujob.service.IResponseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

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
}
