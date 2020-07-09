package com.seven.core;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;


public interface IBaseService<T> extends IService<T> {

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return entity
     */
    T insertReturn(T entity);

	/**
	 * <p>
	 * 更新一条记录
	 * </p>
	 *
	 * @param entity
	 *            实体对象
	 * @return entity
	 */
	T updateByIdReturn(T entity);


    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return entity
     */
    T insertOrUpdateReturn(T entity);

	/**
	 * 批量插入记录
	 * 
	 * @param entityList:要插入的实体对象
	 * @return
	 */
	public List<T> insertBatchReturn(List<T> entityList);

	/**
	 * 批量插入或者更新记录
	 * 
	 * @param entityList:要插入的实体对象
	 * @return
	 */
	public List<T> insertOrUpdateBatchReturn(List<T> entityList);
}
