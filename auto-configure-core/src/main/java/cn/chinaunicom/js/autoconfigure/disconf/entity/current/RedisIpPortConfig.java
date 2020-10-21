package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.IpAndPort;

/**
 * redis 链接地址和端口号配置
 * 
 * @author chenkexiao
 * @date 2019/03/13
 */
@Service
@DisconfFile(filename = "RedisIpPort.json")
public class RedisIpPortConfig implements ConfigFile {
    private List<IpAndPort> redisIpPortList;

    public List<IpAndPort> getRedisIpPortList() {
        return redisIpPortList;
    }

    public void setRedisIpPortList(List<IpAndPort> redisIpPortList) {
        this.redisIpPortList = redisIpPortList;
    }

    @Override
    public String toString() {
        return "RedisIpPortConfig [redisIpPortList=" + redisIpPortList + "]";
    }

}
