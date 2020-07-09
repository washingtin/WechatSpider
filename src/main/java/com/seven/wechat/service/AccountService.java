package com.seven.wechat.service;

import com.seven.core.IBaseService;
import com.seven.wechat.bean.Account;
import com.seven.wechat.bean.Article;
import com.seven.wechat.bean.ReportModel;

/**
 * 
 * @author 最爱吃小鱼
 *
 */
public interface AccountService extends IBaseService<Account> {

    void batchSave(ReportModel model);

    Account selectOne(Account account);
}
