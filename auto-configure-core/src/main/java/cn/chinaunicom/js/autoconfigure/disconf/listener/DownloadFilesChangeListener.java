package cn.chinaunicom.js.autoconfigure.disconf.listener;

import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;
import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;

/**
 * 配置文件更新通知的具体实现类。可通过继承CustomizationChangeListener类，实现自定义监听器
 * 
 * @author chenkexiao
 *
 */
@Service
public class DownloadFilesChangeListener extends CustomizationChangeListener {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(DownloadFilesChangeListener.class);

    @Override
    public void reloadDisconfFile(String fileName, String filePath) {
        logger.info("DownloadFilesChangeListener.reloadDisconfFile begin key: {}  filePath:{}", fileName, filePath);
        if (fileName.equals("CustomFiles.properties")) {
            Properties prop = ConfigBeanUtils.readPropertiesFile(filePath);
            if (prop != null) {
                Iterator<String> it = prop.stringPropertyNames().iterator();
                String custFileName = "";
                while (it.hasNext()) {
                    // 下载文件
                    custFileName = it.next();
                    DisconfUtils.downloadFileFromServerToDir(custFileName);
                    DisconfUtils.refreshFileContentCache(custFileName);
                }
            }

        }
    }

    @Override
    public void reloadDisconfItem(String key, Object content) throws Exception {
        logger.info("DownloadFilesChangeListener.reloadDisconfItem begin key: {}  value:{}", key, content);
        logger.info("DownloadFilesChangeListener.reloadDisconfItem end.");
    }

}
