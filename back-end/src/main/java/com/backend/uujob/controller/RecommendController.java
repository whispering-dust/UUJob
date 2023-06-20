package com.backend.uujob.controller;

import com.backend.uujob.entity.Active;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.UserSimilarity;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IActiveService;
import com.backend.uujob.service.IJobService;
import com.backend.uujob.service.IUserSimilarityService;
import com.backend.uujob.utils.RecommendUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mapleleaf
 * @date 2023年06月05日13:31
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendController {
    @Resource
    private IActiveService activeService;
    @Resource
    private IUserSimilarityService userSimilarityService;
    @Resource
    private IJobService jobService;

    @GetMapping("/hits")
    public Result getAllUserActive(){
        List<Active> activeList= activeService.listAllUserActive();

        if(activeList.size()==0){
            return Result.error(Constants.CODE_500,"数据库为空");
        }
        //System.out.println(activeList);
        return Result.success(activeList);
    }

    @PostMapping("/calculate")
    public Result calculateSimilarity(){
        // 1.查询所有的用户浏览记录
        List<Active> activeList = activeService.listAllUserActive();

        // 2.调用推荐模块工具类的方法组装成一个ConcurrentHashMap来存储每个用户以及其对应的二级类目的点击量
        ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> activeMap = RecommendUtils.assembleUserBehavior(activeList);

        // 3.调用推荐模块工具类的方法计算用户与用户之间的相似度
        List<UserSimilarity> similarityList = RecommendUtils.calcSimilarityBetweenUsers(activeMap);

        // 4.输出计算好的用户之间的相似度
        for (UserSimilarity u_sim : similarityList) {
            //System.out.println(u_sim.getUserId() + "\t" + u_sim.getRefUserId() + "\t" + u_sim.getSimilarity());
            // 5.如果用户之间的相似度已经存在与数据库中就修改，不存在就添加
            if (userSimilarityService.isExistsUserSimilarity(u_sim)) { // 修改
                boolean flag = userSimilarityService.updateUserSimilarity(u_sim);
                if (flag) {
                    //System.out.println("修改数据成功");
                }
                else{
                    return Result.error(Constants.CODE_500,"修改相似度数据失败");
                }
            } else { // 新增
                boolean flag = userSimilarityService.saveUserSimilarity(u_sim);
                if (flag) {
                    //System.out.println("插入数据成功");
                }
                else {
                    return Result.error(Constants.CODE_500,"插入相似度数据失败");
                }
            }
        }
        return Result.success();
    }

    @GetMapping("")
    public Result getRecommendedJobByUserId(@RequestParam Integer id){

        //获取用户的点击数据
        List<Active> allActiveList = activeService.listAllUserActive();
        if(allActiveList.isEmpty()){
            return Result.error(Constants.CODE_500,"所有用户的点击数据为空");
        }

        //获取前3个相似用户（根据需要修改参数）
        List<Integer> userIds= userSimilarityService.getTopNUser(id,3);
        if(userIds.isEmpty()){
            return Result.error(Constants.CODE_500,"无法获取相似用户");
        }

        Set<Integer> recommendPosition = RecommendUtils.getRecommendPosition(id,userIds,allActiveList);
        for (Integer positionId : recommendPosition) {
            System.out.println("被推荐的岗位类别：" + positionId);
        }

        // 找出岗位类别中对应的所有工作
        Set<Job> recommendJobs = new HashSet<>();
        for (Integer positionId : recommendPosition) {
            List<Job> jobList = jobService.getListByPositionId(positionId);

            //获取前三条工作
            for(int i=0;i<3;i++){
                if(i+1<=jobList.size()){
                    recommendJobs.add(jobList.get(i));
                }
            }
            // 找出当前岗位类型中中发布时间最近的工作
        }
        if(recommendJobs.isEmpty()){
            return Result.error(Constants.CODE_500,"推荐岗位的数据为空");
        }
        return Result.success(recommendJobs);
    }
}
