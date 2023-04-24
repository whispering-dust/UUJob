package com.backend.uujob.mapper;

import com.backend.uujob.entity.Comment;
import com.backend.uujob.entity.VO.CommentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author mapleleaf
 * @date 2023年04月07日20:39
 */
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("select comment_table.*,response_table.response_num from (select id,content,date,publisher_id,user_name from comment a LEFT JOIN (select id as user_id,user_name from user) b on a.publisher_id=b.user_id where post_id=#{postId}) comment_table left join (select comment_id,count(*) as response_num from response group by comment_id) response_table on comment_table.id=response_table.comment_id;")
    List<CommentVO> getCommentAndUser(int postId);
}
