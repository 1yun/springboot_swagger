package com.yun.springboot_swagger_demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
//扫描controller下的文件
@ComponentScan(basePackages = {"com.yun.springboot_swagger_demo.controller"})
public class Swagger {
    /*
    * 创建api应用
    * apiInfo 增加API相关信息
    * 通过select()函数返回一个ApiSelectorBuilder 实例  用来控制哪些实例需要暴露接口
    * */

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());

    }
        /*
        * 创建该API的基本信息 基本相信会展示在文档页面中
        * 访问地址  http://项目实际地址/swagger-ui.html
        *
        * */



        private ApiInfo apiInfo(){
            return new ApiInfoBuilder().title("springboot中使用swagger2")
                    .description("更多请关注")
                    .termsOfServiceUrl("http://www.baidu.com")
                    .contact("sunf")
                    .version("1.0")
                    .build();
        }


}
