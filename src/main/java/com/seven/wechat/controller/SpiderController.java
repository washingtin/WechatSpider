package com.seven.wechat.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seven.util.PdfUtil;
import com.seven.util.StringUtil;
import com.seven.wechat.bean.Account;
import com.seven.wechat.bean.Article;
import com.seven.wechat.bean.Comment;
import com.seven.wechat.bean.ReportModel;
import com.seven.wechat.service.AccountService;
import com.seven.wechat.service.ArticleService;
import com.seven.wechat.service.CommentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @author 最爱吃小鱼
 */
@RestController
public class SpiderController {
    private Logger log = LoggerFactory.getLogger(SpiderController.class);

    @Autowired
    AccountService accountService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    // 上传微信公众号信息及文章信息, 历史记录的第一页
    @RequestMapping("/spider/firstpage")
    public void firstpage(String biz, String content) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        ReportModel model = JSONObject.parseObject(content, ReportModel.class);
        if (model == null) {
            return;
        }
        accountService.batchSave(model);
        log.debug("已抓取微信公众号[biz={}]文章{}条", biz, model.getArticles().size());
    }

    // 上传微信公众号信息及文章信息, 历史记录下拉数据
    @RequestMapping("/spider/nextpage")
    public void nextpage(String biz, String content) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        List<Article> articles = JSONArray.parseArray(content, Article.class);
        if (CollectionUtils.isEmpty(articles)) {
            return;
        }
        articleService.batchSave(articles);
        log.debug("已抓取微信公众号[biz={}]文章{}条", biz, articles.size());
    }

    // 更新文章内容
    @RequestMapping("/spider/updateArticleContent")
    public String updateArticleContent(String biz, String content) throws Exception {
        log.info("文章内容={}", content);

        content = StringUtil.encodeEmoji(content);

        if (StringUtils.isBlank(content)) {
            return articleService.selectNextArticleLink(biz, null);
        }

        Article article = JSONArray.parseObject(content, Article.class);
        if (article == null || StringUtils.isBlank(article.getContent())) {
            return articleService.selectNextArticleLink(biz, null);
        }
        if (article.getMid() != null) {
            Article bean = articleService.selectOne(article);
            Account account = accountService.selectOne(new Account(biz, null, null, null));
//            articleService.updateContent(article);
//            PdfUtil.createPDF("/opt/applogs/"+ article.getTitle() +".pdf", article.getContent());
            // 转word文档
//            PdfUtil.htmlToWord2("/opt/applogs/wechat/"+ article.getTitle() +".doc", article.getContent());
            // 转txt文档
            StringBuilder text = new StringBuilder("/opt/applogs/wechat/");
            text.append(account.getNickname());
            File newDir = new File(text.toString());
            if (!newDir.exists()) {
                newDir.mkdirs();
            }
            text.append("/").append(bean.getTitle().replaceAll("/","&")).append(".txt");

            PdfUtil.contentToTxt(text.toString(), article.getContent());
            log.debug("微信公众号文章[biz={}, mid={}]已更新内容", biz, article.getMid());
        }
        return articleService.selectNextArticleLink(biz, article.getMid());
    }

    // 上传微信公众号信息及文章信息, 历史记录下拉数据
    @RequestMapping("/spider/updateArticleNum")
    public void updateArticleNum(String biz, String content) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        Article article = JSONObject.parseObject(content, Article.class);
        if (article == null || article.getReadNum() == null || article.getLikeNum() == null) {
            return;
        }
        articleService.updateReadAndLikeNum(article);
        log.debug("微信公众号文章[biz={}, mid={}]已更新点赞量({})，阅读量({})",
                biz, article.getMid(), article.getLikeNum(), article.getReadNum());
    }

    // 评论
    @RequestMapping("/spider/comment")
    public void comment(String biz, String content) {
        if (StringUtils.isBlank(content)) {
            return;
        }

        content = StringUtil.encodeEmoji(content);

        List<Comment> comments = JSONArray.parseArray(content, Comment.class);

        if (CollectionUtils.isEmpty(comments)) {
            return;
        }
        commentService.bachSave(comments);
        log.debug("微信公众号文章评论[biz={}, 评论数={}]",
                biz, comments.size());
    }
}
