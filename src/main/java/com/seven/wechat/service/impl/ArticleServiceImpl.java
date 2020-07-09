package com.seven.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.seven.core.BaseServiceImpl;
import com.seven.wechat.bean.Article;
import com.seven.wechat.cloud.ArticleMapper;
import com.seven.wechat.service.ArticleService;
import org.apache.commons.collections.CollectionUtils;
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
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    @Async("reportTaskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<Article> articles) {
        if (CollectionUtils.isEmpty(articles)) {
            return;
        }
        for (Article article : articles) {
            if (article.getMid() == null) {
                continue;
            }
            article.setId(IdWorker.getId());
            baseMapper.replaceInsert(article);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContent(Article article) {
        Article bean = selectOne(new EntityWrapper<Article>().eq("biz", article.getBiz()).eq("mid", article.getMid()));
        if (bean == null) {
            return;
        }
        Article updateObj = new Article();
        updateObj.setId(bean.getId());
        updateObj.setContent(article.getContent());
        updateById(updateObj);
    }

    @Override
    @Async("reportTaskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void updateReadAndLikeNum(Article article) {
        Article bean = selectOne(new EntityWrapper<Article>().eq("biz", article.getBiz()).eq("mid", article.getMid()));
        if (bean == null) {
            return;
        }
        Article updateObj = new Article();
        updateObj.setId(bean.getId());
        updateObj.setReadNum(article.getReadNum());
        updateObj.setLikeNum(article.getLikeNum());
        updateById(updateObj);
    }

    @Override
    public String selectNextArticleLink(String biz, Long mid) {
        Wrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.eq("biz", biz);
        wrapper.ne(mid != null, "mid", mid);
        wrapper.isNull("content");
        wrapper.last("limit 1");
        Article next = super.selectOne(wrapper);
        if (next != null) {
            return next.getContentUrl();
        }
        return null;
    }

    @Override
    public Article selectOne(Article article) {
        Article bean = selectOne(new EntityWrapper<Article>().eq("biz", article.getBiz()).eq("mid", article.getMid()));

        return bean;
    }
}
