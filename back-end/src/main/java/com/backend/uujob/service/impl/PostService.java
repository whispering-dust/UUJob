package com.backend.uujob.service.impl;

import com.backend.uujob.controller.dto.PostDTO;
import com.backend.uujob.controller.dto.PostDetailDTO;
import com.backend.uujob.entity.Post;
import com.backend.uujob.enums.CensorStatusEnum;
import com.backend.uujob.mapper.PostMapper;
import com.backend.uujob.service.IPostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

/**
 * @author mapleleaf
 * @date 2023年04月15日21:12
 */
@Service
public class PostService extends ServiceImpl<PostMapper,Post> implements IPostService {

    @Resource
    private PostMapper postMapper;

    @Override
    public List<PostDTO> getPostList() {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("status", CensorStatusEnum.CENSOR_STATUS_PASS.ordinal())  //通过审核的帖子才能显示
                .orderByDesc("date");  //按照帖子创建时间进行降序排序后返回，方便前端显示
        List<Post> res = list(queryWrapper);
        List<PostDTO> result = new ArrayList<>();
        for(Post p : res){
            PostDTO obj = new PostDTO(p);
            result.add(obj);
        }
        return result;
    }

    @Override
    public PostDetailDTO getPostDetailById(int id) {
        PostDetailDTO obj = new PostDetailDTO();

        QueryWrapper<Post> postWrapper = new QueryWrapper<>();
        postWrapper.eq("id", id);  //查找指定帖子

        //处理常规信息
        obj.setTitle(getOne(postWrapper).getTitle());
        obj.setContent(getOne(postWrapper).getContent());

        //处理时间
        Timestamp originalTime=getOne(postWrapper).getDate();
        String date = timeTransfer(originalTime);
        obj.setDate(date);

        //处理帖子类型
        obj.setType(getOne(postWrapper).getType());

        //注意：此处还未添加评论
        return obj;
    }
    @Override
    public List<Post> getListByStatus(CensorStatusEnum censorStatusEnum){
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("status", censorStatusEnum.ordinal())  //满足状态的帖子才能显示
                .orderByDesc("date");  //按照帖子创建时间进行降序排序后返回，方便前端显示
        return list(queryWrapper);
    }
}
