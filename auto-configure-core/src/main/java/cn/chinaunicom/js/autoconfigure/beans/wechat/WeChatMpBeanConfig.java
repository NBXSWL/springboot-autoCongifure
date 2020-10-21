package cn.chinaunicom.js.autoconfigure.beans.wechat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.chinaunicom.js.autoconfigure.beans.jedis.JedisClusterBeanConfig;
import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.wechat.service.TemplateMessageService;
import cn.chinaunicom.js.autoconfigure.wechat.service.WxConfigStorageService;
import cn.chinaunicom.js.autoconfigure.wechat.service.WxTemplateMsgService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import redis.clients.jedis.JedisCluster;

/**
 * @author chenkexiao
 * @date 2019/03/12
 */
@Configuration
@ConditionalFeature(featureName = {"Wechat"})
@ConditionalOnClass({WxMpService.class})
public class WeChatMpBeanConfig {

    @Bean
    @ConditionalOnClass({WxMpService.class})
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxConfigStorageService().getWxConfigStorage());
        return wxMpService;
    }

    @Bean
    @ConditionalOnClass({WxMpService.class})
    public WxConfigStorageService wxConfigStorageService() {
        JedisCluster jedisCluster = new JedisClusterBeanConfig().jedisCluster();
        WxConfigStorageService wxConfigStorageService = new WxConfigStorageService(jedisCluster);
        return wxConfigStorageService;
    }

    @Bean
    @ConditionalOnClass({WxMpService.class})
    public WxTemplateMsgService wxTemplateMsgService() {
        WxTemplateMsgService wxTemplateMsgService = new WxTemplateMsgService(wxMpService());
        return wxTemplateMsgService;
    }

    @Bean
    @ConditionalOnClass({WxMpService.class})
    public TemplateMessageService templateMessageService() {
        TemplateMessageService templateMessageService = new TemplateMessageService(wxTemplateMsgService());
        return templateMessageService;
    }
}
