package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;

/**
 * dubbo生产者配置
 * 
 * @author chenkexiao
 * @date 2019/03/20
 */
@Service
@DisconfFile(filename = "DubboServer.properties")
public class DubboServerConfig implements ConfigFile {
    private String applicationName;
    private String registryAddress;
    private String protocolName;
    private int protocolPort;
    private String protocolHost;
    private String protocolDispatcher;
    private String protocolThreadpool;
    private int protocolThreads;
    private String protocolSerialization;

    private int providerTimeout;

    @DisconfFileItem(name = "server.application.name")
    public String getApplicationName() {
        return this.applicationName;

    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @DisconfFileItem(name = "server.registry.address")
    public String getRegistryAddress() {
        return this.registryAddress;

    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    @DisconfFileItem(name = "server.protocol.name")
    public String getProtocolName() {
        return this.protocolName;

    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    @DisconfFileItem(name = "server.protocol.serialization")
    public String getProtocolSerialization() {
        return this.protocolSerialization;

    }

    public void setProtocolSerialization(String protocolSerialization) {
        this.protocolSerialization = protocolSerialization;
    }

    @DisconfFileItem(name = "server.protocol.port")
    public int getProtocolPort() {
        return this.protocolPort;

    }

    public void setProtocolPort(int protocolPort) {
        this.protocolPort = protocolPort;
    }

    @DisconfFileItem(name = "server.protocol.host")
    public String getProtocolHost() {
        return this.protocolHost;

    }

    public void setProtocolHost(String protocolHost) {
        this.protocolHost = protocolHost;
    }

    @DisconfFileItem(name = "server.protocol.dispatcher")
    public String getProtocolDispatcher() {
        return this.protocolDispatcher;

    }

    public void setProtocolDispatcher(String protocolDispatcher) {
        this.protocolDispatcher = protocolDispatcher;
    }

    @DisconfFileItem(name = "server.protocol.threadpool")
    public String getProtocolThreadpool() {
        return this.protocolThreadpool;

    }

    public void setProtocolThreadpool(String protocolThreadpool) {
        this.protocolThreadpool = protocolThreadpool;
    }

    @DisconfFileItem(name = "server.protocol.threads")
    public int getProtocolThreads() {
        return this.protocolThreads;

    }

    public void setProtocolThreads(int protocolThreads) {
        this.protocolThreads = protocolThreads;
    }

    @DisconfFileItem(name = "server.provider.timeout")
    public int getProviderTimeout() {
        return this.providerTimeout;

    }

    public void setProviderTimeout(int providerTimeout) {
        this.providerTimeout = providerTimeout;
    }

    public String toString() {
        return "DubboServerConfig [applicationName=" + this.applicationName + ", registryAddress="
            + this.registryAddress + ", protocolName=" + this.protocolName + ", protocolPort=" + this.protocolPort
            + ", protocolHost=" + this.protocolHost + ", protocolDispatcher=" + this.protocolDispatcher
            + ", protocolThreadpool=" + this.protocolThreadpool + ", protocolThreads=" + this.protocolThreads
            + ", protocolSerialization=" + this.protocolSerialization + ", providerTimeout=" + this.providerTimeout
            + "]";
    }
}