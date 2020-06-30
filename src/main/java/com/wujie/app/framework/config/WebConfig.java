package com.wujie.app.framework.config;

import com.wujie.app.framework.submit.AvoidDuplicateSubmissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 修改Spring mvc的基本配置
 * 启动时初始化system_cfg_tbl
 * @ClassName: WebConfig.java
 * @author Mr.Chen->MengDaNai
 * @version 1.0
 * @Date 2019年1月29日 下午5:57:31
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer,ErrorPageRegistrar {


	/** 防重复提交的拦截器 **/
	private AvoidDuplicateSubmissionInterceptor avoidDuplicateSubmissionInterceptor;


	/** /static/** **/
	private static final String PATTERNS = "/static/**";

	/** /webjars/** **/
	private static final String WEBJARS = "/webjars/**";

	private static final Long NUMBER_2097_1520 = 2097_1520L;

	private static final Integer NUMBER_104_8576 = 104_8576;


	@Autowired
	public WebConfig(AvoidDuplicateSubmissionInterceptor avoidDuplicateSubmissionInterceptor) {
		this.avoidDuplicateSubmissionInterceptor = avoidDuplicateSubmissionInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(avoidDuplicateSubmissionInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/swagger-resources/**", WEBJARS, "/v2/**", "/swagger-ui.html/**").excludePathPatterns(PATTERNS);
	}

	/**
	 * 上传的基本配置
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(NUMBER_2097_1520);
		multipartResolver.setMaxInMemorySize(NUMBER_104_8576);
		return multipartResolver;
	}

	/**
	 * 静态资源文件配置
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(PATTERNS).addResourceLocations("classpath:/static/");
		registry.addResourceHandler(WEBJARS).addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	}

	/**
	 * 配置404页面
	 */
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage[] errorPages = new ErrorPage[2];
		errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
		errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");
		registry.addErrorPages(errorPages);
	}

}
