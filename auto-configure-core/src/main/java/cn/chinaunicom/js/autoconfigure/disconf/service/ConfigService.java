package cn.chinaunicom.js.autoconfigure.disconf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;

/**
 * 获取disconf上配置的配置信息（base配置+当前项目配置）
 * 
 * @author chenkexiao
 * @date 2019/02/22
 */
public class ConfigService {
    public static Logger logger = LoggerFactory.getLogger(ConfigService.class);

    /**
     * 查询是否已经有记录
     * 
     * @param fileName
     *            配置文件名
     * @return
     */
    public String getConfigByFile(String fileName) {
        logger.info("ConfigService.getConfigByFile begin fileName:{}", fileName);
        String result = DisconfUtils.getConfigByFile(fileName);
        logger.info("ConfigService.getConfigByFile end file content:{}", result);
        return result;
    }

    public String getCfgByFileKey(String fileName, String keyName) {
        logger.info("ConfigService.getCfgByFileKey begin fileName:{},keyName:{}", fileName, keyName);
        String result = DisconfUtils.getCfgByFileKey(fileName, keyName);
        logger.info("ConfigTest.getCfgByFile end file content:{}", result);
        return result;
    }

    /**
     * 查询是否已经有记录
     * 
     * @param key
     *            配置项名称
     * @return
     */
    public String getConfigByKey(String key) {
        logger.info("ConfigService.getConfigByKey begin key:{}", key);
        String result = DisconfUtils.getConfigByKey(key);
        logger.info("ConfigService.getConfigByKey begin value:{}", result);
        return result;
    }

}
