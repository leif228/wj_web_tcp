package com.wujie.app.framework.util.base.impl;


import com.wujie.app.framework.exception.CustomException;
import com.wujie.app.framework.util.base.BaseService;

import java.util.function.Function;

/**
 * 
 *************************************************
 * describe: 提供公用的业务实现                  
 * @author  Mr.Chen->MengDaNai                 
 * @version 1.0                
 * @date    2018年11月8日 创建文件
 * @see                        
 *************************************************
 */
public abstract class BaseServiceImpl implements BaseService {
	
	/**
	 * describe:公用的方法
	 * @param t
	 * @param deal
	 * @return
	 * @throws
	 */
	@Override
	public <T, R> R base(T t, Function<T, R> deal) throws CustomException {
		return deal.apply(t);
	}
	
}
