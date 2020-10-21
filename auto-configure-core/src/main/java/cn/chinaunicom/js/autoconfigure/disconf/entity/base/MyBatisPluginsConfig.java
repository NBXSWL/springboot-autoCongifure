package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import java.util.HashMap;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

/**
 * MyBatis插件信息
 * 
 * <pre>
 * 配置文件内容格式: {"package.className":{"key4":"4","key3":"3","key2":"2","key1":"1","key0":"0"}}
 * </pre>
 * 
 * @author chenkexiao
 * @date 2019/03/19
 */
@Service
@DisconfFile(filename = "MyBatisPlugins.json", app = "base")
public class MyBatisPluginsConfig extends HashMap<String, Properties> implements ConfigFile {
    /**
     *
     */
    private static final long serialVersionUID = 2194423013515993996L;

}
