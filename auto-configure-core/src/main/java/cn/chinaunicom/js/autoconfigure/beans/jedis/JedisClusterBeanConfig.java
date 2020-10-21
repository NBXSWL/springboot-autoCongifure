package cn.chinaunicom.js.autoconfigure.beans.jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.disconf.entity.base.RedisPoolConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.current.RedisIpPortConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.IpAndPort;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author chenkexiao
 * @date 2019/03/13
 */
@Configuration
@ConditionalFeature(featureName = {"Jedis"})
@ConditionalOnClass({JedisCluster.class, JedisPoolConfig.class})
public class JedisClusterBeanConfig {
    @Bean
    @ConditionalOnClass({JedisPoolConfig.class})
    public JedisPoolConfig jedisPoolConfig() {
        RedisPoolConfig redisPoolConfig = ConfigBeanUtils.getBean(RedisPoolConfig.class);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(redisPoolConfig.getMinIdle());
        jedisPoolConfig.setMaxIdle(redisPoolConfig.getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisPoolConfig.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisPoolConfig.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(redisPoolConfig.isTestOnReturn());
        return jedisPoolConfig;
    }

    /**
     * 注意： 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
     * 
     * @return
     */
    @Bean
    @ConditionalOnClass({JedisCluster.class})
    public JedisCluster jedisCluster() {
        List<IpAndPort> redisIpPortList =
            ((RedisIpPortConfig)ConfigBeanUtils.getBean(RedisIpPortConfig.class)).getRedisIpPortList();
        Set<HostAndPort> nodes = new HashSet<>();
        for (IpAndPort redisIpPort : redisIpPortList) {
            nodes.add(new HostAndPort(redisIpPort.getIp(), redisIpPort.getPort()));
        }
        JedisCluster jedisCluster = new JedisCluster(nodes, jedisPoolConfig());
        return jedisCluster;
    }

}