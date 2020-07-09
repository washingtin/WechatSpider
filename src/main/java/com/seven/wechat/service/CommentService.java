package com.seven.wechat.service;


import com.seven.core.IBaseService;
import com.seven.wechat.bean.Comment;

import java.util.List;

/**
 * 
 * @author 最爱吃小鱼
 *
 */
public interface CommentService extends IBaseService<Comment> {

    void bachSave(List<Comment> comments);
}
