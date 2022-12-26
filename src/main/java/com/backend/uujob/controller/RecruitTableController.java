package com.backend.uujob.controller;

import com.backend.uujob.common.Result;
import com.backend.uujob.controller.dto.RecruitTableDTO;
import com.backend.uujob.exception.ServiceException;
import com.backend.uujob.service.IRecruitTableService;
import com.backend.uujob.service.impl.RecruitTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recruitTable")
public class RecruitTableController {

    private final IRecruitTableService recruitTableService;
    @Autowired
    public RecruitTableController(RecruitTableService recruitTableService) {
        this.recruitTableService = recruitTableService;
    }

    /**
     * 获取所有招聘信息
     * @return 获取结果
     */
    @GetMapping("/allTable")
    public Result allRecruitTable() {
        try {
            return Result.success(recruitTableService.getAllRecruitTable());
        } catch (ServiceException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 获取用户发布的招聘信息
     * @param id 用户ID
     * @return 获取结果
     */
    @GetMapping("/myPost")
    public Result myPost(@RequestParam int id){
        try{
            return Result.success(recruitTableService.getPostRecruitTable(id));
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 发布招聘信息
     * @param recruitTableDTO 招聘信息数据传输对象
     * @return 发布结果
     */
    @PostMapping("/post")
    public Result postRT(@RequestBody RecruitTableDTO recruitTableDTO){//有效数据仅含title、position、salary、description
        try{
            recruitTableService.postRecruitTable(recruitTableDTO);
            return Result.success(recruitTableDTO);
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 删除招聘信息
     * @param tableId 招聘信息ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result deleteRT(@RequestParam int tableId){
        try{
            return Result.success(recruitTableService.deleteRT(tableId));
        }catch(ServiceException e){
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
