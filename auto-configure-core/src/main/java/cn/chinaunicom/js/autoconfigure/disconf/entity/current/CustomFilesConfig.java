package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;

/**
 * 定制配置文件下载器，k-v：disconf创建的配置文件名：disconf创建的配置文件名
 * 
 * @author chenkexiao 项目配置信息文件托管(仅在项目启动时，下载到classpath路径下)
 */
@Service
@DisconfFile(filename = "CustomFiles.properties")
public class CustomFilesConfig implements ConfigFile {

}
