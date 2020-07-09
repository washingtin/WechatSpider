package com.seven.wechat.cloud;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seven.wechat.bean.Article;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 最爱吃小鱼
 *
 */
public interface ArticleMapper extends BaseMapper<Article>{

    void replaceInsert(@Param("bean") Article article);
}
