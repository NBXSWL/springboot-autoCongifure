package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import org.springframework.stereotype.Component;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * redis连接池配置信息
 * 
 * @author chenkexiao
 * @date 2019/03/13
 */
@Component
@DisconfFile(filename = "RedisPool.properties", app = "base")
public class RedisPoolConfig implements ConfigFile {
    // 最小能够保持idel状态的对象数
    private int minIdle;
    // 最大能够保持idel状态的对象数
    private int maxIdle;
    // 最大分配的对象数
    private int maxTotal;
    // 最大分配的对象数
    private long maxWaitMillis;
    // 当调用borrow Object方法时，是否进行有效性检查
    private boolean testOnReturn;

    @DisconfFileItem(name = "base.redis.minIdle")
    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    @DisconfFileItem(name = "base.redis.maxIdle")
    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    @DisconfFileItem(name = "base.redis.maxTotal")
    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    @DisconfFileItem(name = "base.redis.maxWaitMillis")
    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    @DisconfFileItem(name = "base.redis.testOnReturn")
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    @Override
    public String toString() {
        return "RedisPoolConfig [minIdle=" + minIdle + ", maxIdle=" + maxIdle + ", maxTotal=" + maxTotal
            + ", maxWaitMillis=" + maxWaitMillis + ", testOnReturn=" + testOnReturn + "]";
    }

}
