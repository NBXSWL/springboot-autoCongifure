package cn.chinaunicom.js.autoconfigure.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.model.DisconfCenterFile;
import com.baidu.disconf.client.common.model.DisconfCenterFile.FileItemValue;
import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.client.store.DisconfStoreProcessor;
import com.baidu.disconf.client.store.DisconfStoreProcessorFactory;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.GlobalConfig;

/**
 * 缓存配置信息类实例
 * 
 * @author chenkexiao
 *
 */
public class ConfigBeanUtils {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(ConfigBeanUtils.class);
    private static Map<Class<? extends ConfigFile>, ConfigFile> beanCache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> cls) {
        logger.info("ConfigBeanUtils.getBean begin cls: {}", cls);
        if (cls != null) {
            if (beanCache.containsKey(cls)) {
                logger.info("ConfigBeanUtils.getBean0 ConfigFile:{}", cls.getName());
                return (T)beanCache.get(cls);
            }
            Reflections reflections = DisconfUtils.getReflections();
            Set<Class<? extends ConfigFile>> subTypes = reflections.getSubTypesOf(ConfigFile.class);
            for (Class<? extends ConfigFile> subClass : subTypes) {
                if (subClass == cls) {
                    DisconfFile disconfFile = subClass.getAnnotation(DisconfFile.class);
                    if (disconfFile != null && !DisClientConfig.getInstance().getDisableFeatureFileNamesSet()
                        .contains(disconfFile.filename())) {
                        reloadValueToConfigBean(subClass, disconfFile);
                    }
                }
            }
        } else {
            Reflections reflections = DisconfUtils.getReflections();
            Set<Class<? extends ConfigFile>> subTypes = reflections.getSubTypesOf(ConfigFile.class);
            for (Class<? extends ConfigFile> subClass : subTypes) {
                DisconfFile disconfFile = subClass.getAnnotation(DisconfFile.class);
                if (disconfFile != null && !DisClientConfig.getInstance().getDisableFeatureFileNamesSet()
                    .contains(disconfFile.filename())) {
                    reloadValueToConfigBean(subClass, disconfFile);
                }
            }
        }

        return (T)beanCache.get(cls);
    }

    /**
     * 刷新配置类实例信息
     * 
     * @param subClass
     * @param disconfFile
     */
    public static void reloadValueToConfigBean(Class<? extends ConfigFile> subClass, DisconfFile disconfFile) {
        String fileName = disconfFile.filename();
        DisconfStoreProcessor disconfStoreProcessor = DisconfStoreProcessorFactory.getDisconfStoreFileProcessor();
        DisconfCenterFile disconfCenterFile = (DisconfCenterFile)disconfStoreProcessor.getConfData(fileName);
        String filePath = disconfCenterFile.getFilePath();
        ConfigFile configFile = getConfileInstance(subClass);

        if (fileName.toLowerCase().endsWith(FileTypeConsts.JSON_SUFFIX)) {
            configFile = readJsonFile(subClass, fileName, filePath);
            setBean(subClass, configFile);
        }

        if (fileName.toLowerCase().endsWith(FileTypeConsts.PROPERTIES_SUFFIX)) {
            Map<String, FileItemValue> keyMap = disconfCenterFile.getKeyMaps();
            Properties prop = readPropertiesFile(filePath);
            loadValueToGlobal(prop);
            loadValueToConfigBean(keyMap, subClass);
        }
        logger.info("ConfigBeanUtils.getBean configFile: {}", configFile);
    }

    /**
     * 加载每个properties文件的内容到对应的配置类
     * 
     * @param keyMap
     * @param subClass
     */
    private static void loadValueToConfigBean(Map<String, FileItemValue> keyMap, Class<? extends ConfigFile> subClass) {
        logger.info("ConfigBeanUtils.loadValueToConfigBean begin FileItemValue:{},subClass:{}", keyMap,
            subClass.getName());
        try {
            ConfigFile configFile = subClass.newInstance();
            for (Map.Entry<String, FileItemValue> entry : keyMap.entrySet()) {
                FileItemValue fileItemValue = entry.getValue();
                if (fileItemValue.getValue() != null) {
                    fileItemValue.setValue4FileItem(configFile, fileItemValue.getValue());
                }
            }
            setBean(subClass, configFile);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("ConfigBeanUtils.loadValueToConfigBean Exception:{}", e);
        } catch (Exception e) {
            logger.error("ConfigBeanUtils.loadValueToConfigBean Exception:{}", e);
        }
        logger.info("ConfigBeanUtils.loadValueToConfigBean end ");
    }

    /**
     * 将所有的properties配置文件内容加载到全局配置类
     * 
     * @param prop
     */
    private static void loadValueToGlobal(Properties prop) {
        Iterator<String> it = prop.stringPropertyNames().iterator();
        String propKey = "";
        while (it.hasNext()) {
            propKey = it.next();
            // 放入全局配置信息类
            GlobalConfig.putConfig(propKey, prop.getProperty(propKey));
        }
    }

    /**
     * 从properties文件读取文件内容
     * 
     * @param filePath
     * @param globalConfig
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Properties readPropertiesFile(String filePath) {
        logger.info("ConfigBeanUtils.loadContentFromProperties filePath: {} ", filePath);
        FileInputStream in = null;
        Properties prop = new Properties();
        try {
            in = new FileInputStream(new File(filePath));
            prop.load(in);
        } catch (IOException e) {
            logger.error("ConfigBeanUtils.loadContentFromProperties Exception: {} ", e.getMessage());
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("ConfigBeanUtils.loadContentFromProperties file close Exception: {} ", e.getMessage());
                }
            }
        }
        logger.info("ConfigBeanUtils.reloadDisconfFile loadContentFromProperties: {} ", GlobalConfig.getKeyValue());
        return prop;
    }

    /**
     * 读取json格式配置文件
     * 
     * @param subClass
     * @param fileName
     * @param filePath
     * @return
     */
    public static ConfigFile readJsonFile(Class<? extends ConfigFile> subClass, String fileName, String filePath) {
        ConfigFile jsonConfigFile;
        logger.info("ConfigBeanUtils.getBean begin fileName: {} filePath:{}", fileName, filePath);
        String str = "";
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                str = FileUtils.readFileToString(file);
            }
        } catch (IOException e) {
            logger.error("ConfigBeanUtils.getBean IOException", e);
        }
        jsonConfigFile = GsonUtils.gsonToBean(str, subClass);
        logger.info("ConfigBeanUtils.getBean jsonConfigFile: {} ", jsonConfigFile);
        return jsonConfigFile;
    }

    public static void setBean(Class<? extends ConfigFile> cls, ConfigFile jsonConfigFile) {
        beanCache.put(cls, jsonConfigFile);
    }

    /**
     * @param subClass
     * @return
     */
    private static ConfigFile getConfileInstance(Class<? extends ConfigFile> subClass) {
        logger.info("ConfigBeanUtils.getConfileInstance begin subClass: {}", subClass);
        ConfigFile configFile = beanCache.get(subClass);
        if (configFile == null) {
            try {
                    configFile = subClass.newInstance();
            } catch (InstantiationException e) {
                logger.info("ConfigBeanUtils.getConfileInstance Exception0: {}", e);
            } catch (IllegalAccessException e) {
                logger.info("ConfigBeanUtils.getConfileInstance Exception1: {}", e);
            }
        }
        logger.info("ConfigBeanUtils.getConfileInstance end configFile: {}", configFile);
        return configFile;
    }

}
