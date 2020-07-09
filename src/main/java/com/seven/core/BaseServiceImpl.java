package com.seven.core;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {


    @Override
    @Transactional
    public T insertReturn(T entity) {
        super.insert(entity);
        return entity;
    }

    @Override
    @Transactional
    public T updateByIdReturn(T entity) {
        super.updateById(entity);
        String id = null;
        try {
            id = BeanUtils.getProperty(entity, "id");
        } catch (Exception e) {
            // ignore
        }
        if (id == null) {
            throw new RuntimeException("主键值为空");
        }
        return super.selectById(id);
    }

    @Override
    @Transactional
    public T insertOrUpdateReturn(T entity) {
        if (null == entity) {
            return null;
        } else {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal)) {
                    return this.insertReturn(entity);
                } else {
                    return this.updateByIdReturn(entity);
                }
            } else {
                throw new MybatisPlusException("Error:  Can not execute. Could not find @TableId.");
            }
        }
    }

	@Override
    @Transactional
	public List<T> insertBatchReturn(List<T> entityList) {
		super.insertBatch(entityList);
		return entityList;
	}

	@Override
    @Transactional
	public List<T> insertOrUpdateBatchReturn(List<T> entityList) {
		super.insertOrUpdateBatch(entityList);
		return entityList;
	}

    @Override
    public Page<T> selectPage(Page<T> page) {
        return selectPage(page, Condition.create());
    }

    @Override
    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        if (SqlHelper.isEmptyOfWrapper(wrapper)) {
            wrapper = Condition.create();
        }
        return super.selectPage(page, wrapper);
    }
}
