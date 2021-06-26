package com.dgl.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

@Configuration
@EnableKnife4j
@Profile({"dev","docker"})
@EnableSwagger2WebMvc
public class Knife4jConfig {


	/**
	 * 创建获取api应用
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//这里采用包含注解的方式来确定要显示的接口(建议使用这种)
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * 配置swagger文档显示的相关内容标识(信息会显示到swagger页面)
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("杜光磊", "http://localhost:9000/doc.html", "dgl00098@163.com");
		return new ApiInfo("尚品宅配服务平台",
				"此接口文档仅供内部查看",
				"v1.0",
				"www.wuxin.com",
				contact, "可爱的小果果",
				"可爱的小果果",
				new ArrayList<>()
		);
	}
}

