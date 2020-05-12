package com.chenfei.kafkademo.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {


	@Bean(value = "defaultApi2")
	public Docket defaultApi2() {
		Docket docket=new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				//分组名称
				.groupName("2.X版本")
				.select()
				//这里指定Controller扫描包路径
				.apis(RequestHandlerSelectors.basePackage("com.chenfei.kafkademo"))
				.paths(PathSelectors.any())
				.build();
		return docket;
	}


	/**
	 * 创建 API 信息
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("测试接口文档示例")
				.description("我是一段描述")
				.version("1.0.0") // 版本号
				.contact(new Contact("ChanFi", "http://www.iocoder.cn", "test@gmail.com")) // 联系人
				.build();
	}

}
