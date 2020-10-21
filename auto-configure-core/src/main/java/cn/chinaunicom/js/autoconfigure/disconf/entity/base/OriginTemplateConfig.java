package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 原始的微信模板消息格式配置类
 * 
 * @author chenkexiao
 * @date 2019/02/22
 */
@Service
@DisconfFile(filename = "OriginTemplate.json", app = "base")
@ConditionalFeature(featureName = {"Wechat"})
public class OriginTemplateConfig extends HashMap<String, WxMpTemplateMessage> implements ConfigFile {

    /**
     *
     */
    private static final long serialVersionUID = -7733585022681143987L;

}
