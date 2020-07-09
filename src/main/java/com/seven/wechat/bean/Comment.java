package com.seven.wechat.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 数据库表[comment]的数据模型
 * 微信文章评论
 * 
 * 
 * @author 最爱吃小鱼
 * 
 */

@TableName("comment")
@SuppressWarnings("serial")
public class Comment implements Serializable {

	@TableId
	private Long id;
    // 公众号唯一标识 
    @TableField("biz")
    private String biz;
    // 文章的mid 
    @TableField("mid")
    private Long mid;
    // 评论ID
    @TableField("content_id")
    private String contentId;
    // 昵称 
    @TableField("nick_name")
    private String nickName;
    // 头像 
    @TableField("logo_url")
    private String logoUrl;
    // 评论内容 
    @TableField("content")
    private String content;
    // 创建时间 
    @TableField("create_time")
    private Integer createTime;
    // 点赞量 
    @TableField("like_num")
    private Integer likeNum;
    
    /** 默认构造函数 */
    public Comment() {
    }
    
    /** 全参构造函数 */
    public Comment(String biz, Long mid, String contentId, String nickName, String logoUrl, String content, Integer createTime, Integer likeNum) {
      this.biz = biz;
      this.mid = mid;
      this.contentId = contentId;
      this.nickName = nickName;
      this.logoUrl = logoUrl;
      this.content = content;
      this.createTime = createTime;
      this.likeNum = likeNum;
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
    public  Long getMid() {
        return this.mid;
    }
    public void setMid(Long mid) {
        this.mid = mid;
    }
    public  String getContentId() {
        return this.contentId;
    }
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    public  String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public  String getLogoUrl() {
        return this.logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    public  String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public  Integer getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    public  Integer getLikeNum() {
        return this.likeNum;
    }
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
}
