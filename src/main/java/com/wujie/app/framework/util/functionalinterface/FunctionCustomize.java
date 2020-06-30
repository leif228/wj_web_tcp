package com.wujie.app.framework.util.functionalinterface;

/**
 * 
 *************************************************
 * describe:自定义的Function抛出Exception异常
 * @author  MengDaNai                   
 * @version 1.0                
 * @date    2019年1月29日 创建文件                                            
 * @See                            
 *************************************************
 */
@FunctionalInterface
public interface FunctionCustomize<T, R> {
	
	R apply(T t) throws Exception;
}
