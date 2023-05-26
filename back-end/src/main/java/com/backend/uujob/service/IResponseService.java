package com.backend.uujob.service;

import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.Response;
import com.backend.uujob.entity.VO.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月23日20:55
 */
public interface IResponseService  extends IService<Response> {
    List<ResponseVO> getResponsesByCommentId(int id);

    List<Response> getListByUserId(int userId);
}
