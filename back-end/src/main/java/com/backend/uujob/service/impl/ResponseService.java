package com.backend.uujob.service.impl;


import com.backend.uujob.vo.ResponseVO;
import com.backend.uujob.entity.Response;
import com.backend.uujob.mapper.ResponseMapper;
import com.backend.uujob.service.IResponseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月23日20:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResponseService extends ServiceImpl<ResponseMapper, Response> implements IResponseService {
    @Resource
    private ResponseMapper responseMapper;
    
    @Override
    public List<ResponseVO> getResponsesByCommentId(int id) {
        return responseMapper.getResponseVOList(id);
    }
    
    @Override
    public List<Response> getListByUserId(int userId) {
        QueryWrapper<Response> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publisher_id", userId)  //寻找该用户发布的回复
                .orderByDesc("date");  //按照帖子创建时间进行降序排序后返回，方便前端显示
        return list(queryWrapper);
    }
}
