package com.seven.wechat.cloud;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seven.wechat.bean.Comment;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 最爱吃小鱼
 *
 */
public interface CommentMapper extends BaseMapper<Comment>{

    void replaceInsert(@Param("bean") Comment comment);
}
