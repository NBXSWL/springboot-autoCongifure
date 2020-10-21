package cn.chinaunicom.js.autoconfigure.beans.spring;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ejoined.commons.plugin.utils.SpringContextHolder;

import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.disconf.listener.StartupListenerForDisconf;

@Configuration
@ConditionalOnClass({ApplicationContextAware.class})
public class SpringToolsBeanConfig {

    @Bean
    @ConditionalOnClass({ApplicationContextAware.class})
    public SpringContextHolder springContextHolder() {
        SpringContextHolder springContextHolder = new SpringContextHolder();
        return springContextHolder;
    }

    @Bean
    @ConditionalOnClass({ApplicationContextAware.class})
    public StartupListenerForDisconf startupListener() {
        StartupListenerForDisconf startupListener = new StartupListenerForDisconf();
        return startupListener;
    }
}
