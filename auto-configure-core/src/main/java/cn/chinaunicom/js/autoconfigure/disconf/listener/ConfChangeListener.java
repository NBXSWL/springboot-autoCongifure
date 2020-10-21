package cn.chinaunicom.js.autoconfigure.disconf.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.update.IDisconfUpdatePipeline;
import com.ejoined.commons.plugin.utils.SpringContextHolder;

import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;

/**
 * 配置更新通知，下发给实现CustomizationChangeListener的子类
 * 
 * @author chenkexiao
 *
 */
@Service
public class ConfChangeListener implements IDisconfUpdatePipeline {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(ConfChangeListener.class);

    @SuppressWarnings("unchecked")
    @Override
    public void reloadDisconfFile(String fileName, String filePath) throws Exception {
        logger.info("ConfChangeListener.reloadDisconfFile begin key: {}  filePath:{}", fileName, filePath);
        Reflections reflections = DisconfUtils.getReflections();
        Set<Class<? extends CustomizationChangeListener>> subTypes =
            reflections.getSubTypesOf(CustomizationChangeListener.class);
        for (Class<? extends CustomizationChangeListener> subClass : subTypes) {
            Set<Method> results = ReflectionUtils.getMethods(subClass, ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withName("reloadDisconfFile"));
            for (Method method : results) {
                method.invoke(SpringContextHolder.getBean(subClass), fileName, filePath);
            }
        }
        logger.info("ConfChangeListener.reloadDisconfFile end.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void reloadDisconfItem(String key, Object content) throws Exception {
        logger.info("ConfChangeListener.reloadDisconfItem begin key: {}  content:{}", key, content);
        Reflections reflections = DisconfUtils.getReflections();
        Set<Class<? extends CustomizationChangeListener>> subTypes =
            reflections.getSubTypesOf(CustomizationChangeListener.class);
        for (Class<? extends CustomizationChangeListener> subClass : subTypes) {
            Set<Method> results = ReflectionUtils.getMethods(subClass, ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withName("reloadDisconfItem"));
            for (Method method : results) {
                method.invoke(SpringContextHolder.getBean(subClass), key, content);
            }
        }
        logger.info("ConfChangeListener.reloadDisconfItem end.");
    }

}
