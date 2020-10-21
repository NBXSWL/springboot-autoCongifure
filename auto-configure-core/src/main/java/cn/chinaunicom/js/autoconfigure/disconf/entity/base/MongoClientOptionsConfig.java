package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * MongoDb链接配置信息
 * 
 * @author chenkexiao
 * @date 2019/03/19
 */
@DisconfFile(filename = "MongoClientOptions.properties", app = "base")
public class MongoClientOptionsConfig implements ConfigFile {
    private int minConnectionsPerHost;
    private int threadsAllowedToBlockForConnectionMultiplier;
    private int connectTimeout;
    private int maxWaitTime;
    private boolean socketKeepAlive;
    private int socketTimeout;
    private int maxConnectionIdleTime;
    private int maxConnectionLifeTime;
    private int heartbeatSocketTimeout;
    private int heartbeatConnectTimeout;
    private int minHeartbeatFrequency;
    private int heartbeatFrequency;

    @DisconfFileItem(name = "base.mongo.client.options.minConnectionsPerHost")
    public int getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(int minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    @DisconfFileItem(name = "base.mongo.client.options.threadsAllowedToBlockForConnectionMultiplier")
    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    @DisconfFileItem(name = "base.mongo.client.options.connectTimeout")
    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    @DisconfFileItem(name = "base.mongo.client.options.maxWaitTime")
    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    @DisconfFileItem(name = "base.mongo.client.options.socketKeepAlive")
    public boolean isSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    @DisconfFileItem(name = "base.mongo.client.options.socketTimeout")
    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    @DisconfFileItem(name = "base.mongo.client.options.maxConnectionIdleTime")
    public int getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(int maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    @DisconfFileItem(name = "base.mongo.client.options.maxConnectionLifeTime")
    public int getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(int maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    @DisconfFileItem(name = "base.mongo.client.options.heartbeatSocketTimeout")
    public int getHeartbeatSocketTimeout() {
        return heartbeatSocketTimeout;
    }

    public void setHeartbeatSocketTimeout(int heartbeatSocketTimeout) {
        this.heartbeatSocketTimeout = heartbeatSocketTimeout;
    }

    @DisconfFileItem(name = "base.mongo.client.options.heartbeatConnectTimeout")
    public int getHeartbeatConnectTimeout() {
        return heartbeatConnectTimeout;
    }

    public void setHeartbeatConnectTimeout(int heartbeatConnectTimeout) {
        this.heartbeatConnectTimeout = heartbeatConnectTimeout;
    }

    @DisconfFileItem(name = "base.mongo.client.options.minHeartbeatFrequency")
    public int getMinHeartbeatFrequency() {
        return minHeartbeatFrequency;
    }

    public void setMinHeartbeatFrequency(int minHeartbeatFrequency) {
        this.minHeartbeatFrequency = minHeartbeatFrequency;
    }

    @DisconfFileItem(name = "base.mongo.client.options.heartbeatFrequency")
    public int getHeartbeatFrequency() {
        return heartbeatFrequency;
    }

    public void setHeartbeatFrequency(int heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    @Override
    public String toString() {
        return "MongoDbConfig [minConnectionsPerHost=" + minConnectionsPerHost
            + ", threadsAllowedToBlockForConnectionMultiplier=" + threadsAllowedToBlockForConnectionMultiplier
            + ", connectTimeout=" + connectTimeout + ", maxWaitTime=" + maxWaitTime + ", socketKeepAlive="
            + socketKeepAlive + ", socketTimeout=" + socketTimeout + ", maxConnectionIdleTime=" + maxConnectionIdleTime
            + ", maxConnectionLifeTime=" + maxConnectionLifeTime + ", heartbeatSocketTimeout=" + heartbeatSocketTimeout
            + ", heartbeatConnectTimeout=" + heartbeatConnectTimeout + ", minHeartbeatFrequency="
            + minHeartbeatFrequency + ", heartbeatFrequency=" + heartbeatFrequency + "]";
    }

}
