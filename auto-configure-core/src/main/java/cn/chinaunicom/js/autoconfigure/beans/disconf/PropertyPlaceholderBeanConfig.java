package cn.chinaunicom.js.autoconfigure.beans.disconf;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;

import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;

/**
 * 非注解式（托管式）：配置文件没有相应的配置注解类，此配置文件不会被注入到配置类中。disconf只是简单的对其进行“托管”。
 * 启动时下载配置文件；配置文件变化时，负责动态推送。注意，此种方式，程序不会自动reload配置，需要自己写回调函数。
 *
 * @author chenkexiao
 * @since 2019-01-10
 */
@Component
@ConditionalOnClass({ReloadablePropertiesFactoryBean.class, PropertyPlaceholderConfigurer.class})
public class PropertyPlaceholderBeanConfig {
    @Bean
    public ReloadablePropertiesFactoryBean propFactoryBean() {
        ReloadablePropertiesFactoryBean res = new ReloadablePropertiesFactoryBean();
        res.setLocations(DisconfUtils.getSysCfgFileList());
        return res;
    }

    @Bean
    public PropertyPlaceholderConfigurer propPlaceholderBean() throws IOException {
        PropertyPlaceholderConfigurer holder = new PropertyPlaceholderConfigurer();
        holder.setIgnoreResourceNotFound(true);
        holder.setIgnoreUnresolvablePlaceholders(true);
        holder.setPropertiesArray(propFactoryBean().getObject());
        return holder;
    }
}