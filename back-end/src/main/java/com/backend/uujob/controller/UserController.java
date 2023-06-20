package com.backend.uujob.controller;

import com.backend.uujob.entity.*;
import com.backend.uujob.entity.VO.CommentVO;
import com.backend.uujob.entity.VO.JobVO;
import com.backend.uujob.entity.VO.ResponseVO;
import com.backend.uujob.entity.VO.StarVO;
import com.backend.uujob.enums.ApplStatusEnum;
import com.backend.uujob.enums.StarTypeEnum;
import com.backend.uujob.result.Constants;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

/**
 * @author mapleleaf
 * @date 2023年03月28日21:39
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IProfileService profileService;
    @Resource
    private IApplicationService applicationService;
    @Resource
    private IJobService jobService;
    @Resource
    private IStarService starService;
    @Resource
    private IPostService postService;
    @Resource
    private ICommentService commentService;
    @Resource
    private IResponseService responseService;

    /**
     * @description 通过id查找一个用户
     * @author mapleleaf
     * @date 2023/3/28 21:59
     */
    @GetMapping("")
    public Result getUserById(@RequestParam int id) {
            return Result.success(userService.getById(id));
    }

    @PutMapping("")
    public Result updateUserInfo(@RequestBody User user) {
        if(userService.updateById(user)){
            return Result.success(userService.getById(user.getId()));
        }
        return Result.error();
    }

    @PostMapping("/registration")
    public Result registration(@RequestBody User user) {
        User targetUser = userService.getByUserName(user.getUserName());  //由于用户名唯一，需要检验用户名是否存在
        if(targetUser != null){
            return Result.error(Constants.CODE_500,"用户已存在");
        }


        user.setStatus(0);  //用户默认状态为未封禁
        userService.save(user);

        //为用户创建初始简历
        Profile profile = new Profile();
        profile.setSeekerId(user.getId());
        profile.setAdmissionDate(new java.sql.Timestamp(System.currentTimeMillis()));
        profile.setGraduationDate(new java.sql.Timestamp(System.currentTimeMillis()));
        profileService.save(profile);
        return Result.success(user.getId());
    }

    @PostMapping ("/login")
    public Result login(@RequestBody User user) {
        User targetUser = userService.getByUserName(user.getUserName());  //先检查用户是否存在

        if(targetUser == null){
            return Result.error(Constants.CODE_500,"用户不存在");
        }

        if(targetUser.getPassword().equals(user.getPassword())){  //若存在则检验密码是否正确
            return Result.success(targetUser.getId());
        }

        return Result.error(Constants.CODE_500,"密码错误");
    }

    @GetMapping("/profiles")
    public Result getProfileByUserId(@RequestParam int userId){
        Profile targetProfile = profileService.getByUserId(userId);
        if(targetProfile == null){
            return Result.error(Constants.CODE_500,"该用户尚未创建简历");
        }
        return Result.success(targetProfile);
    }

    @GetMapping("/applications")
    public Result getApplicationByUserId(@RequestParam int userId){
        List<Profile> profileList = profileService.getListByUserId(userId);  //先获取用户拥有的简历
        if(profileList.isEmpty()){
            return Result.error(Constants.CODE_500,"该用户尚未创建简历");
        }

        List<Application> applicationList = new ArrayList<>();
        for(Profile p : profileList){
            Application res = applicationService.getByProfileId(p.getId()); //再根据简历查找他被投递到哪些岗位
            applicationList.add(res);
        }

        List<JobVO> jobList = new ArrayList<>();
        for(Application a : applicationList){
            Job j = jobService.getById(a.getJobId());

            JobVO ja = new JobVO();  //填入申请的基本信息
            ja.setJobId(j.getId());
            ja.setTitle(j.getTitle());
            ja.setPosition(j.getPosition());
            ja.setLocation(j.getLocation());
            ja.setSalary(j.getSalary());
            ja.setApplicationDate(timeTransfer(a.getApplicationDate()));
            ja.setStatus(a.getStatus());
            if(ja.getStatus() != ApplStatusEnum.APPL_STATUS_SUBMIT.ordinal()){  //申请有结果才需要添加批复日期，否则不需要
                ja.setReviewDate(timeTransfer(a.getReviewDate()));
            }
            ja.setProfile(profileService.getById(a.getProfileId()));

            jobList.add(ja);
        }
        return Result.success(jobList);
    }

    @GetMapping("/jobs")
    public Result getJobByUserId(@RequestParam int userId){

        List<Job> jobList = jobService.getListByUserId(userId);
        System.out.println(jobList);
        if(jobList == null){
            return Result.error(Constants.CODE_500,"该用户尚未发布岗位");
        }
        return Result.success(jobList);
    }

    @GetMapping("/stars")
    public Result getStarByUserId(@RequestParam int userId){
        List<Star> starList = starService.getByUserId(userId);  //获取用户的所有收藏信息
        List<StarVO> res = new ArrayList<>();
        for(Star s : starList){  //根据收藏信息收集完整的显示信息
            StarVO starVO = new StarVO();
            starVO.setId(s.getId());
            starVO.setTargetId(s.getTargetId());
            starVO.setStarType(s.getStarType());
            if(starVO.getStarType() == StarTypeEnum.STAR_TYPE_POST.ordinal()){  //根据收藏的类别选择对应的标题
                Post post = postService.getById(starVO.getTargetId());
                starVO.setTitle(post.getTitle());
            }
            if(starVO.getStarType() == StarTypeEnum.STAR_TYPE_JOB.ordinal()){
                Job job = jobService.getById(starVO.getTargetId());
                starVO.setTitle(job.getTitle());
            }
            res.add(starVO);
        }

        return Result.success(res);
    }

    @GetMapping("/posts")
    public Result getPostByUserId(@RequestParam int userId){
        List<Post> postList = postService.getListByUserId(userId);
        if(postList == null){
            return Result.error(Constants.CODE_500,"该用户尚未发布帖子");
        }
        return Result.success(postList);
    }

    @GetMapping("/comments")
    public Result getCommentByUserId(@RequestParam int userId){
        List<Comment> commentList = commentService.getListByUserId(userId);
        if(commentList == null){
            return Result.error(Constants.CODE_500,"该用户尚未发布评论");
        }

        List<CommentVO> commentVOList = new ArrayList<>();  //设置commentVO信息
        for(Comment c : commentList){
            CommentVO commentVO = new CommentVO();
            commentVO.setId(c.getId());
            commentVO.setDate(timeTransfer(c.getDate()));
            commentVO.setPublisherId(c.getPublisherId());
            commentVO.setUserName(userService.getNameById(c.getPublisherId()));
            commentVO.setContent(c.getContent());
            commentVO.setResponseNum(responseService.getResponsesByCommentId(c.getId()).size());
            commentVOList.add(commentVO);
        }
        return Result.success(commentVOList);
    }

    @GetMapping("/responses")
    public Result getResponseByUserId(@RequestParam int userId){

        List<Response> responseList = responseService.getListByUserId(userId);
        if(responseList == null){
            return Result.error(Constants.CODE_500,"该用户尚未发布回复");
        }

        List<ResponseVO> responseVOList = new ArrayList<>();  //设置responseVO信息
        for(Response r : responseList){
            ResponseVO responseVO = new ResponseVO();
            responseVO.setId(r.getId());
            responseVO.setContent(r.getContent());
            responseVO.setDate(timeTransfer(r.getDate()));
            responseVO.setPublisherId(r.getPublisherId());
            responseVO.setPublisherName(userService.getNameById(r.getPublisherId()));
            if(r.getTargetUserId() != null){
                responseVO.setTargetUserId(r.getTargetUserId());
                responseVO.setTargetUserName(userService.getNameById(r.getTargetUserId()));
            }
            responseVOList.add(responseVO);
        }
        return Result.success(responseVOList);
    }

}
