package com.wujie.app.framework.util.base;


import com.wujie.app.framework.exception.CustomException;
import com.wujie.app.framework.util.functionalinterface.FunctionCustomize;
import com.wujie.app.framework.util.functionalinterface.PredicateCustomize;

import java.util.function.*;

/**
 * 
 *************************************************
 * describe:公用的业务接口                  
 * @author  Mr.Chen->MengDaNai    
 * @version 1.0                
 * @date    2018年11月8日 创建文件                                             
 * @see                        
 *************************************************
 */
public interface BaseService {

	/**
	 *************************************************
	 * describe:公用的方法
	 * @author  MengDaNai
	 * @param t 
	 * @param deal 接收T对象，返回R对象
	 * @return r
	 * @throws CustomException 异常类
	 * @date    2019年3月19日 创建文件         
	 *************************************************
	 */
	<T,R> R base(T t, Function<T, R> deal) throws CustomException;
	
	/**
	 *************************************************
	 * describe:公用的大方法
	 * @author  MengDaNai
	 * @param t
	 * @param u
	 * @param deal 接收T,U对象，返回R对象
	 * @return r
	 * @throws CustomException 异常类
	 * @date    2019年3月19日 创建文件         
	 *************************************************
	 */
	default <T, U, R> R base(T t, U u, BiFunction<T, U, R> deal) throws CustomException {
		return deal.apply(t, u);
	}
	
	/**
	 *************************************************
	 * describe:公用的生产数据
	 * @author  MengDaNai
	 * @param supplier 生产一个t
	 * @return t
	 * @throws CustomException
	 * @date    2019年3月19日 创建文件         
	 *************************************************
	 */
	default <T> T base(Supplier<T> supplier) throws CustomException{
		return supplier.get();
	}
	
	/**
	 *************************************************
	 * describe:公用的消费数据 
	 * @author  MengDaNai
	 * @param t
	 * @param consumer 消费一个t
	 * @throws CustomException 异常类
	 * @date    2019年3月19日 创建文件         
	 *************************************************
	 */
	default <T> void base(T t, Consumer<T> consumer) throws CustomException {
		consumer.accept(t);
	}
	
	/**
	 *************************************************
	 * describe:公用的谓词数据校验 
	 * @author  MengDaNai
	 * @param t
	 * @param check 接收一个t返回boolean
	 * @return boolean
	 * @throws CustomException 异常类
	 * @date    2019年3月19日 创建文件         
	 *************************************************
	 */
	default <T> boolean baseCheck(T t, Predicate<T> check) throws CustomException {
		return check.test(t);
	}
	
	/**
	 *************************************************
	 * describe:公用的方法 
	 * @author  MengDaNai
	 * @param t
	 * @param deal 接收T对象，返回R对象
	 * @return r
	 * @throws Exception 异常
	 * @date    2019年3月19日 创建文件         
	 *************************************************
	 */
	default <T, R> R baseException(T t, FunctionCustomize<T, R> deal) throws Exception {
		return deal.apply(t);
	}
	
	/**
	 *************************************************
	 * describe:公用的谓词数据校验 
	 * @author  MengDaNai
	 * @param t
	 * @param check 接收一个t返回boolean
	 * @return boolean
	 * @throws Exception 异常
	 * @date    2019年3月19日 创建文件         
	 *************************************************
	 */
	default <T> boolean baseCheckException(T t, PredicateCustomize<T> check) throws Exception {
		return check.test(t);
	}

}
