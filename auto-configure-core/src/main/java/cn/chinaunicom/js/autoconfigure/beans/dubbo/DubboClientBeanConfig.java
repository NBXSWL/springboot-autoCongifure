package cn.chinaunicom.js.autoconfigure.beans.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.disconf.entity.current.DubboClientConfig;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;

/**
 * dubbo配置类.
 *
 * @author chenkexiao
 * @since 2019-01-10
 */
@Configuration
@ConditionalFeature(featureName = {"DubboClient"})
@ConditionalOnClass({ApplicationConfig.class})
public class DubboClientBeanConfig {

    /**
     * 应用名配置
     *
     * @return
     */
    @Bean
    @ConditionalOnClass({ApplicationConfig.class})
    public ApplicationConfig clientApplicationConfig() {
        DubboClientConfig dubboClientConfig = ConfigBeanUtils.getBean(DubboClientConfig.class);
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboClientConfig.getApplicationName());
        return applicationConfig;
    }

    /**
     * 地址配置
     *
     * @return
     */
    @Bean
    @ConditionalOnClass({RegistryConfig.class})
    public RegistryConfig clientRegistryConfig() {
        DubboClientConfig dubboClientConfig = ConfigBeanUtils.getBean(DubboClientConfig.class);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboClientConfig.getRegistryAddress());
        return registryConfig;
    }

    /**
     * 协议配置
     * 
     * @return
     */
    @Bean
    @ConditionalOnClass({ProtocolConfig.class})
    public ProtocolConfig clientProtocolConfig() {
        DubboClientConfig dubboClientConfig = ConfigBeanUtils.getBean(DubboClientConfig.class);
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(dubboClientConfig.getProtocolName());
        protocolConfig.setPort(dubboClientConfig.getProtocolPort());
        protocolConfig.setSerialization(dubboClientConfig.getProtocolSerialization());
        return protocolConfig;
    }
}
