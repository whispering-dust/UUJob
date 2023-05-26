package com.backend.uujob.mapper;

import com.backend.uujob.entity.Response;
import com.backend.uujob.entity.VO.ResponseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月07日20:27
 */
public interface ResponseMapper extends BaseMapper<Response> {
    @Select("SELECT r.id, r.content, r.date, pu.id AS publisher_id,pu.user_name AS publisher_name, tu.id AS target_user_id,tu.user_name AS target_user_name FROM response AS r INNER JOIN user AS pu ON r.publisher_id = pu.id LEFT JOIN user AS tu ON r.target_user_id = tu.id WHERE r.comment_id = #{commentId};")
    List<ResponseVO> getResponseVOList(int commentId);
}
