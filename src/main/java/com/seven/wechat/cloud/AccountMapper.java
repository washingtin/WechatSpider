package com.seven.wechat.cloud;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seven.wechat.bean.Account;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 最爱吃小鱼
 *
 */
public interface AccountMapper extends BaseMapper<Account> {

    void replaceInsert(@Param("bean") Account parent);
}
