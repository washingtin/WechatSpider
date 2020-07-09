package com.seven.wechat.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.seven.util.Assert;
import com.seven.wechat.bean.Article;
import com.seven.wechat.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信公众号文章的维护
 * 
 * @author 最爱吃小鱼
 *
 */
@RestController
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	// 微信公众号文章的列表
	@RequestMapping("/article/list")
	public Page<Article> list(Page page) {
		return articleService.selectPage(page);
	}

	// 微信公众号文章的详情
	@RequestMapping("/article/detail")
	public Article detail(Long id) {
		Assert.noParams(id);
		Article record = articleService.selectById(id);
		Assert.isNull("微信公众号文章不存在", record);
		return record;
	}

	// 微信公众号文章的删除
	@RequestMapping("/article/remove")
	public void remove(Long id) {
		Assert.noParams(id);
		articleService.deleteById(id);
	}
	
}
