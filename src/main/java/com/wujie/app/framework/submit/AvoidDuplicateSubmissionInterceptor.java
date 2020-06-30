package com.wujie.app.framework.submit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wujie.app.framework.result.GeneralResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

/**
 * 
 *************************************************
 * 功能描述:  防止重复提交的拦截器                  
 * @author  Mr.Chen                   
 * @version 1.0                
 * @date    2019年1月9日 创建文件                                             
 * @see                        
 *************************************************
 */
// @Configuration
@Slf4j
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
	private static final String TOKEN = "token";
	/**
	 * 预处理器
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof ResourceHttpRequestHandler){
			return true;
		}
		//获取到方法上的注解
		Token annotation = null;
		try{
			annotation = getAnnotation(handler, Token.class);
		}catch (Exception e){
			return true;
		}

		//说明方法需要进行拦截
		if(Objects.isNull(annotation)) {
			return true;
		}
		log.debug("deal submit");
		
		//是否需要验证创建口令
		boolean needSaveSession = annotation.createToken();
		//是否移除口令
		boolean needRemoveSession = annotation.needRemoveToken();
		//是否检查口令并修改口令的值
		boolean checkAndUpdateToken = annotation.checkAndUpdateToken();
		if(needSaveSession) {
			//创建口令
			request.getSession(false).setAttribute(TOKEN, UUID.randomUUID().toString());
		}else if(needRemoveSession) {
			//移除口令
			if(!isValidSubmit(request)){
				//进行对应的异常提示
				errorHandler(response);
				return false;
			}
			//移除session
			request.getSession(false).removeAttribute(TOKEN);
		}else if(checkAndUpdateToken){
			//检查口令并修改口令的值
			if(!isValidSubmit(request)){
				//进行对应的异常提示
				errorHandler(response);
				return false;
			}
			String token = UUID.randomUUID().toString();
			request.getSession().setAttribute(TOKEN, token);
			return true;
		}
		return true;
	}
	
	/**
	 * 后置处理器
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(!(handler instanceof ResourceHttpRequestHandler)){
			//获取到方法上的注解
			try{
				Token annotation = getAnnotation(handler, Token.class);
				if (!Objects.isNull(annotation) && !Objects.isNull(modelAndView) && annotation.createToken()) {
					log.info("token to client");
					Object token = request.getSession().getAttribute(TOKEN);
					if(token != null){
						modelAndView.addObject(TOKEN, token.toString());
					}
				}
			}catch (Exception e){

			}
		}
	}	
	
	/**
	 * 获取到方法上的注解
	 * @param handler
	 * @param annotation
	 * @return
	 */
	private <T extends Annotation> T getAnnotation(Object handler, Class<T> annotation) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		return method.getAnnotation(annotation);
	}
	
	/**
	 * 校验是否重复提交
	 * @param request
	 * @return
	 */
	private boolean isValidSubmit(HttpServletRequest request){
		//校验是否存在token
		Object token = request.getSession().getAttribute(TOKEN);
		if (Objects.isNull(token)){
			log.warn("不正确的提交方式：[user:" + request.getRemoteUser() + ",url:" + request.getServletPath() + "]");
			return false;
		}
		//获取到服务器端的token和客户端所提交的token进行比对
		String serverToken = WebUtils.getSessionAttribute(request, TOKEN).toString();
		String clientToken = request.getParameter(TOKEN);
		if(Objects.isNull(clientToken)){
			log.warn("不正确的提交方式：[user:" + request.getRemoteUser() + ",url:" + request.getServletPath() + "]");
			return false;
		}
		if(!serverToken.equals(clientToken)){
			log.warn("不正确的提交方式：[user:" + request.getRemoteUser() + ",url:" + request.getServletPath() + "]");
			return false;
		}
		return true;
	}
	
	/**
	 * 进行对应的异常处理
	 * @param response
	 * @throws IOException 
	 */
	private void errorHandler(HttpServletResponse response) throws IOException{
		GeneralResult result = new GeneralResult(-1, "错误的提交方式");
		response.setContentType("application/json;charset=UTF-8");
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		outputStream.write(mapper.writeValueAsString(result).getBytes("utf-8"));
		outputStream.close();
	}
}
