package com.wujie.app.framework.cache.ecache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *************************************************
 * 功能描述:  定义模糊删除缓存的切面                  
 * @author  Mr.Chen                   
 * @version 1.0                
 * @date    2019年3月8日 创建文件                                             
 * @see                        
 *************************************************
 */
@Aspect
@Configuration
@Slf4j
public class CacheRemoveAspect {

	/**
	 * 定义切点
	 */
	@Pointcut(value = "(execution(* *.*(..)) && @annotation(com.wujie.app.framework.cache.ecache.CacheRemove))")
	private void pointcut(){}
	
	
	@After(value = "pointcut()")
	private void process(JoinPoint joinPoint){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Object[] args = joinPoint.getArgs(); //切面方法的参数
	    Method method = signature.getMethod(); //切面方法
	    CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);
	    if (cacheRemove != null){
	    	/** 获取到所有的缓存的value */
	        String[] values = cacheRemove.value();
	        String[] keys = cacheRemove.key(); //需要移除的正则key
	        //遍历所有的value
	        Arrays.asList(values).forEach(value -> {
	        	//得到指定value中的所有的key
	        	@SuppressWarnings("unchecked")
				List<Object> cacheKeys = CacheUtils.cacheKeys(value);
	        	//遍历指定所有的正则表达式key
	        	Arrays.asList(keys).stream().forEach(key -> {
	        		//转换指定的key
	        		key = parseKey(key, method, args);
	        		//定义正则匹配
	        		Pattern pattern = Pattern.compile(key);
	        		//遍历所有指定value中的所有key
	        		cacheKeys.stream().map(cacheKey -> cacheKey.toString()).forEach(cacheKey -> {
	        			if (pattern.matcher(cacheKey).find()){
	        				log.info("移除了Value为：" + value + ",cacheKey为："+ cacheKey);
	        				CacheUtils.remove(value, cacheKey);
	        			}
	        		});
	        	});
	        });
	    }
	}
	

    private String parseKey(String key,Method method,Object [] args){           
        //获取被拦截方法参数名列表(使用Spring支持类库)  
        LocalVariableTableParameterNameDiscoverer u =     
            new LocalVariableTableParameterNameDiscoverer();    
        String [] paraNameArr=u.getParameterNames(method);  
          
        //使用SPEL进行key的解析  
        ExpressionParser parser = new SpelExpressionParser();   
        //SPEL上下文  
        StandardEvaluationContext context = new StandardEvaluationContext();  
        //把方法参数放入SPEL上下文中  
        for(int i=0;i<paraNameArr.length;i++){  
            context.setVariable(paraNameArr[i], args[i]);  
        } 
        List<String> pList = descFormat(key);//获取#p0这样的表达式
        //将p0作为参数放入SPEL上下文中
        for(String p:pList) {
        	context.setVariable(p.substring(1), args[Integer.valueOf(p.substring(2))]);
        }
        return parser.parseExpression(key).getValue(context,String.class);  
    } 
    
    /**
     * 提取出#p[数字]这样的表达式
     * @param desc
     * @return
     */
    private static List<String> descFormat(String desc){  
        List<String> list = new ArrayList<>();  
        Pattern pattern = Pattern.compile("#p[0-9]+");   
        Matcher matcher = pattern.matcher(desc);   
        while(matcher.find()){   
            String t = matcher.group(0);   
            list.add(t);  
        }  
        return list;  
    }

}
