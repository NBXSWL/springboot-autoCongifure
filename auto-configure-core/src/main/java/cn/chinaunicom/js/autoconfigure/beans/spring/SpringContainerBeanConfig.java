package cn.chinaunicom.js.autoconfigure.beans.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;

/**
 * Spring 容器启动扫描，替代Spring-context配置文件
 * 
 * @author chenkexiao
 * @date 2019/03/27
 */
@Configuration
@ConditionalOnClass({ApplicationContext.class})
@ComponentScan(value = {"cn.chinaunicom.js", "com.ejoined.octopus"},
    excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
        @Filter(type = FilterType.CUSTOM, classes = {CustomTypeFilter.class})})
public class SpringContainerBeanConfig {

}
