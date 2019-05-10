package com.bwie.seckill.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class swagger2Config {
    public Docket creatRestApi(){
        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        token.name("token_id").description("user_token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(token.build());
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bwie.seckill.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JPA学习")
                .description("许婷正在学JPA")
                .termsOfServiceUrl("www.baidu.com")
                .version("vl")
                .build();
    }
}
