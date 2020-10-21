package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

/**
 * api加固接口白名单，在此列表的接口url不进行任何安全校验
 * 
 * @author chenkexiao
 * @date 2019/11/19
 */
@Service
@DisconfFile(filename = "AllowUrls.json", app = "base")
public class AllowUrlConfig extends HashSet<String> implements ConfigFile {

    /**
     *
     */
    private static final long serialVersionUID = 2848778934109270439L;

}
