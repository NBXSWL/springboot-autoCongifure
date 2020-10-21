package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

/**
 * api加固接口refer参数白名单，在此配置的refer允许调用接口
 * 
 * @author chenkexiao
 * @date 2019/11/19
 */
@Service
@DisconfFile(filename = "AllowReferDomains.json", app = "base")
public class AllowReferDomainConfig extends HashSet<String> implements ConfigFile {

    /**
     *
     */
    private static final long serialVersionUID = -4704698523954425242L;

}
