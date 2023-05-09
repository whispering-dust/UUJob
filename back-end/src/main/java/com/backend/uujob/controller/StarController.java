package com.backend.uujob.controller;

import com.backend.uujob.entity.Star;
import com.backend.uujob.enums.StarTypeEnum;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IStarService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stars")
public class StarController {
    @Resource
    private IStarService starService;

    @PostMapping("/jobs")
    public Result starJob(@RequestBody Star star){
        star.setStarType(StarTypeEnum.STAR_TYPE_JOB.ordinal());
        starService.save(star);
        return Result.success(star.getId());
    }

    @PostMapping("/posts")
    public Result starPost(@RequestBody Star star){
        star.setStarType(StarTypeEnum.STAR_TYPE_POST.ordinal());
        starService.save(star);
        return Result.success(star.getId());
    }

    @PostMapping("")
    public Result getStar(@RequestBody Star star){
        Star s = starService.getByAll(star);
        if(s == null){
            return Result.success(-1);
        }
        return Result.success(s.getId());
    }

    @DeleteMapping("")
    public Result deleteStar(@RequestParam Integer id){
        starService.removeById(id);
        return Result.success();
    }
}
