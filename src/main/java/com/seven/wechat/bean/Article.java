package com.seven.wechat.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 数据库表[article]的数据模型
 * 微信公众号文章
 * 
 * 
 * @author 最爱吃小鱼
 * 
 */

@TableName("article")
@SuppressWarnings("serial")
public class Article implements Serializable {

	@TableId
	private Long id;
    // 公众号唯一标识 
    @TableField("biz")
    private String biz;
    // 公众号文章的ID
    @TableField("mid")
    private Long mid;
    // 文章标题 
    @TableField("title")
    private String title;
    // 文章副标题 
    @TableField("digest")
    private String digest;
    // 文章内容 
    @TableField("content")
    private String content;
    // 微信的详细连接地址 
    @TableField("content_url")
    private String contentUrl;
    // 原文地址 
    @TableField("source_url")
    private String sourceUrl;
    // 作者 
    @TableField("author")
    private String author;
    // 文章图片连接 
    @TableField("cover")
    private String cover;
    // 文章是否原创标识 11为原创 100为无原创 101为转发 
    @TableField("copyright_stat")
    private Integer copyrightStat;
    // 阅读量 
    @TableField("read_num")
    private Integer readNum;
    // 点赞量 
    @TableField("like_num")
    private Integer likeNum;
    // 发布时间 
    @TableField("datetime")
    private Long datetime;
    // 文章发布位置，首条、二条等等
    @TableField("idx")
    private Integer idx;

    /** 默认构造函数 */
    public Article() {
    }
     
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    public  String getBiz() {
        return this.biz;
    }
    public void setBiz(String biz) {
        this.biz = biz;
    }
    public  String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public  String getDigest() {
        return this.digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    public  String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public  String getContentUrl() {
        return this.contentUrl;
    }
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
    public  String getSourceUrl() {
        return this.sourceUrl;
    }
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    public  String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public  String getCover() {
        return this.cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public  Integer getCopyrightStat() {
        return this.copyrightStat;
    }
    public void setCopyrightStat(Integer copyrightStat) {
        this.copyrightStat = copyrightStat;
    }
    public  Integer getReadNum() {
        return this.readNum;
    }
    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }
    public  Integer getLikeNum() {
        return this.likeNum;
    }
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
    public  Long getDatetime() {
        return this.datetime;
    }
    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }
    public Long getMid() {
        return mid;
    }
    public void setMid(Long mid) {
        this.mid = mid;
    }
    public Integer getIdx() {
        return idx;
    }
    public void setIdx(Integer idx) {
        this.idx = idx;
    }
}
