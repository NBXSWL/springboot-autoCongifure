package cn.chinaunicom.js.autoconfigure.disconf.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 配置更新通知父类，如果监听某个配置文件或者配置项变更通知，统一继承此类
 * 
 * @author chenkexiao
 *
 */
@Service
public class CustomizationChangeListener {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(CustomizationChangeListener.class);

    public void reloadDisconfFile(String key, String filePath) throws Exception {
        logger.info("CustomizationChangeListener.reloadDisconfFile key:{} filePath:{}", key, filePath);
    }

    public void reloadDisconfItem(String key, Object content) throws Exception {
        logger.info("CustomizationChangeListener.reloadDisconfItem key:{} content:{}", key, content);
    }

}
