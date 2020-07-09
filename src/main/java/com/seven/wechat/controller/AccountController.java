package com.seven.wechat.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.seven.util.Assert;
import com.seven.wechat.bean.Account;
import com.seven.wechat.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信公众号信息的维护
 * 
 * @author 最爱吃小鱼
 *
 */
@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	// 微信公众号信息的列表
	@RequestMapping("/account/list")
	public Page<Account> list(Page page) {
		return accountService.selectPage(page);
	}

	// 微信公众号信息的详情
	@RequestMapping("/account/detail")
	public Account detail(Long id) {
		Assert.noParams(id);
		Account record = accountService.selectById(id);
		Assert.isNull("微信公众号不存在", record);
		return record;
	}

	// 微信公众号信息的删除
	@RequestMapping("/account/remove")
	public void remove(Long id) {
		Assert.noParams(id);
		accountService.deleteById(id);
	}
	
}
