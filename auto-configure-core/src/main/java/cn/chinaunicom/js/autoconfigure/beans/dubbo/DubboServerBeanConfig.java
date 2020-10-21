package cn.chinaunicom.js.autoconfigure.beans.dubbo;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.disconf.entity.current.DubboServerConfig;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;

/**
 * dubbo配置类.
 *
 * @author chenkexiao
 * @since 2019-01-10
 */
@Configuration
@ConditionalFeature(featureName = {"DubboServer"})
@ConditionalOnClass({ApplicationConfig.class})
public class DubboServerBeanConfig {

    /**
     * 应用名
     *
     * @return
     */
    @Bean
    @ConditionalOnClass({ApplicationConfig.class})
    public ApplicationConfig applicationConfig() {
        DubboServerConfig dubboServerConfig = ConfigBeanUtils.getBean(DubboServerConfig.class);
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboServerConfig.getApplicationName());
        return applicationConfig;
    }

    /**
     * <dubbo:provider timeout="10000" />
     *
     * @return
     */
    @Bean
    @ConditionalOnClass({ProviderConfig.class})
    public ProviderConfig providerConfig() {
        DubboServerConfig dubboServerConfig = ConfigBeanUtils.getBean(DubboServerConfig.class);
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(dubboServerConfig.getProviderTimeout());
        return providerConfig;
    }

    /**
     * 地址配置 <dubbo:registry address="zookeeper://127.0.0.1:2181" client="zkclient"/>
     *
     * @return
     */
    @Bean
    @ConditionalOnClass({RegistryConfig.class})
    public RegistryConfig registryConfig() {
        DubboServerConfig dubboServerConfig = ConfigBeanUtils.getBean(DubboServerConfig.class);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboServerConfig.getRegistryAddress());
        return registryConfig;
    }

    /**
     * 协议配置，等同于 <dubbo:protocol name="dubbo" port="20880" />
     *
     * @return ProtocolConfig
     */
    @Bean
    @ConditionalOnClass({ProtocolConfig.class})
    public ProtocolConfig protocolConfig() {
        DubboServerConfig dubboServerConfig = ConfigBeanUtils.getBean(DubboServerConfig.class);
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(dubboServerConfig.getProtocolName());
        protocolConfig.setPort(dubboServerConfig.getProtocolPort());
        protocolConfig.setDispatcher(dubboServerConfig.getProtocolDispatcher());
        protocolConfig.setHost(dubboServerConfig.getProtocolHost());
        if (StringUtils.isNotEmpty(dubboServerConfig.getProtocolSerialization())) {
            protocolConfig.setSerialization(dubboServerConfig.getProtocolSerialization());
        }
        protocolConfig.setThreadpool(dubboServerConfig.getProtocolThreadpool());
        protocolConfig.setThreads(dubboServerConfig.getProtocolThreads());
        return protocolConfig;
    }
}
