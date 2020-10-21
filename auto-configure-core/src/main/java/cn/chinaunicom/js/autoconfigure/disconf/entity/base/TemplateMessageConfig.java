package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.TemplateMessage;

/**
 * 
 * @author chenkexiao 模板消息配置信息
 */
@Service
@DisconfFile(filename = "TemplateMessageMap.json", app = "base")
public class TemplateMessageConfig extends HashMap<String, TemplateMessage> implements ConfigFile {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
}
