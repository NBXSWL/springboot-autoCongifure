package cn.chinaunicom.js.autoconfigure.disconf.entity.pub;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 全局配置项 key-value配置
 * 
 * @author chenkexiao
 *
 */
public class GlobalConfig {

    private static volatile Map<String, String> map = new HashMap<>();

    public static String getConfigByKey(String confKey) {
        return map.get(confKey);
    }

    public static void putConfig(String confKey, String value) {
        map.put(confKey, value);
    }

    /**
     * @return
     */
    public static Set<Map.Entry<String, String>> getKeyValue() {
        return map.entrySet();
    }

}
