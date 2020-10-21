package cn.chinaunicom.js.autoconfigure.disconf.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;

/**
 * 项目启动时，将json文件配置信息，加载至对应bean实例中
 * 
 * @author chenkexiao
 *
 */
@Service
public class StartupListenerForDisconf implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(StartupListenerForDisconf.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        ConfigBeanUtils.getBean(null);
    }

}