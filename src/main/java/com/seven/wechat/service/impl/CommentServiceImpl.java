package com.seven.wechat.service.impl;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.seven.core.BaseServiceImpl;
import com.seven.wechat.bean.Comment;
import com.seven.wechat.cloud.CommentMapper;
import com.seven.wechat.service.CommentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * @author 最爱吃小鱼
 *
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    @Async("reportTaskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void bachSave(List<Comment> comments) {
        for (Comment comment: comments) {
            if (comment.getBiz() == null || comment.getMid() ==null || comment.getContentId() ==null) {
                continue;
            }
            comment.setId(IdWorker.getId());
            baseMapper.replaceInsert(comment);
        }
    }
}
