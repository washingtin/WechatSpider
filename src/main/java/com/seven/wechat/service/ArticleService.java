package com.seven.wechat.service;


import com.seven.core.IBaseService;
import com.seven.wechat.bean.Article;

import java.util.List;

/**
 * 
 * @author 最爱吃小鱼
 *
 */
public interface ArticleService extends IBaseService<Article> {

    void batchSave(List<Article> articles);

    void updateContent(Article article);

    void updateReadAndLikeNum(Article article);

    String selectNextArticleLink(String biz, Long mid);

    Article selectOne(Article article);
}
