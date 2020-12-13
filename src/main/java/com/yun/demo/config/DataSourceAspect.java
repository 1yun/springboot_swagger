package com.yun.demo.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 数据源切面配置
 * Order设置优先级
 *
 * @author leiwenwei
 */
@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {
    /**
     * 设置切面范围
     */
    @Pointcut("@within(com.yun.demo.config.DataSource) || @annotation(com.yun.demo.config.DataSource)")
    public void pointCut() {

    }

    /**
     * 添加数据源上下文
     *
     * @param dataSource
     */
    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource) {
        log.info("选择数据源---" + dataSource.value().getValue());
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());

    }

    /**
     * 清除数据源上下文
     */
    @After("pointCut()")
    public void doAfter() {
        DataSourceContextHolder.clear();
    }
}
