package com.backend.uujob.service.impl;


import com.backend.uujob.entity.Response;
import com.backend.uujob.entity.VO.ResponseVO;
import com.backend.uujob.mapper.ResponseMapper;
import com.backend.uujob.service.IPostService;
import com.backend.uujob.service.IResponseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月23日20:56
 */
@Service
public class ResponseService extends ServiceImpl<ResponseMapper, Response> implements IResponseService{
    @Resource
    private ResponseMapper responseMapper;
    @Override
    public List<ResponseVO> getResponsesByCommentId(int id) {
        return responseMapper.getResponseVOList(id);
    }
}
