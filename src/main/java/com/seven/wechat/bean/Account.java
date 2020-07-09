package com.seven.wechat.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 数据库表[account]的数据模型
 * 微信公众号信息
 * 
 * 
 * @author 最爱吃小鱼
 * 
 */

@TableName("account")
@SuppressWarnings("serial")
public class Account implements Serializable {

	@TableId
	private Long id;
    // 公众号的唯一标识 
    @TableField("biz")
    private String biz;
    // 昵称 
    @TableField("nickname")
    private String nickname;
    // 公众号LOGO 
    @TableField("headimg")
    private String headimg;
    // 抓取时间，时间戳 
    @TableField("crawl_time")
    private Long crawlTime;
    
    /** 默认构造函数 */
    public Account() {
    }
    
    /** 全参构造函数 */
    public Account(String biz, String nickname, String headimg, Long crawlTime) {
      this.biz = biz;
      this.nickname = nickname;
      this.headimg = headimg;
      this.crawlTime = crawlTime;
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
    public  String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public  String getHeadimg() {
        return this.headimg;
    }
    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
    public  Long getCrawlTime() {
        return this.crawlTime;
    }
    public void setCrawlTime(Long crawlTime) {
        this.crawlTime = crawlTime;
    }
}
