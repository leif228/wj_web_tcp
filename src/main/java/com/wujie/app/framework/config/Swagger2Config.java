package com.wujie.app.framework.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tanghu
 * @date 2019年6月4日
 * @describe Swagger2配置
 */
@Data
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger", ignoreUnknownFields = false)
public class Swagger2Config {
	private String title;
	private String description;
	private String version;
	private String termsOfServiceUrl;
	private String name;
	private String url;
	private String email;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)//
				.apiInfo(apiInfo())//
				.select()//
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//
				.paths(PathSelectors.any())//
				.build()//
				.securitySchemes(securitySchemes())//
				.securityContexts(securityContexts());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()//
				.title(title)//
				.description(description)//
				.termsOfServiceUrl(termsOfServiceUrl)//
				.contact(new Contact(name, url, email))//
				.version(version)//
				.build();
	}

	private List<ApiKey> securitySchemes() {
		List<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
		return apiKeys;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = Lists.newArrayList();
		securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$")).build());
		return securityContexts;
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = Lists.newArrayList();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}

}
