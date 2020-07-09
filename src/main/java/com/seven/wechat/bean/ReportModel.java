package com.seven.wechat.bean;

import java.util.List;

/**
 * @author 最爱吃小鱼
 */
public class ReportModel extends Account {

    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Account parent() {
        return new Account(getBiz(), getNickname(), getHeadimg(), getCrawlTime());
    }
}
