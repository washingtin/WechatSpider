package com.seven.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.seven.core.BaseServiceImpl;
import com.seven.wechat.bean.Account;
import com.seven.wechat.bean.ReportModel;
import com.seven.wechat.cloud.AccountMapper;
import com.seven.wechat.service.AccountService;
import com.seven.wechat.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 最爱吃小鱼
 *
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account> implements AccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    ArticleService articleService;

    @Override
    @Async("reportTaskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(ReportModel model) {
        if (model == null) {
            return;
        }
        Account account = model.parent();
        account.setId(IdWorker.getId());
        try {
            baseMapper.replaceInsert(account);
        } catch (Exception se) {
            logger.error("replace insert into 异常,原因={}", se);
        }

        articleService.batchSave(model.getArticles());
    }

    @Override
    public Account selectOne(Account account) {
        Account bean = selectOne(new EntityWrapper<Account>().eq("biz", account.getBiz()));
        return bean;
    }
}
