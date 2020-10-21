package cn.chinaunicom.js.autoconfigure.disconf.listener;

import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;
import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;

/**
 * 配置文件更新通知的具体实现类。可通过继承CustomizationChangeListener类，实现自定义监听器
 * 
 * @author chenkexiao
 *
 */
@Service
public class ConfFileChangeListener extends CustomizationChangeListener {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(ConfFileChangeListener.class);

    @Override
    public void reloadDisconfFile(String fileName, String filePath) {
        logger.info("ConfFileChangeListener.reloadDisconfFile begin key: {}  filePath:{}", fileName, filePath);
        Reflections reflections = DisconfUtils.getReflections();
        Set<Class<? extends ConfigFile>> subTypes = reflections.getSubTypesOf(ConfigFile.class);
        for (Class<? extends ConfigFile> subClass : subTypes) {
            DisconfFile disconfFile = subClass.getAnnotation(DisconfFile.class);
            if (disconfFile != null && fileName.equals(disconfFile.filename())) {
                ConfigBeanUtils.reloadValueToConfigBean(subClass, disconfFile);
            }
        }
    }

    @Override
    public void reloadDisconfItem(String key, Object content) throws Exception {
        logger.info("ConfFileChangeListener.reloadDisconfItem begin key: {}  value:{}", key, content);
        logger.info("ConfFileChangeListener.reloadDisconfItem end.");
    }

}
