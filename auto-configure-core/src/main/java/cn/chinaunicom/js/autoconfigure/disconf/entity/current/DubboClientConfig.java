package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;

/**
 * dubbo消费者配置
 * 
 * @author chenkexiao
 * @date 2019/03/20
 */
@Service
@DisconfFile(filename = "DubboClient.properties")
public class DubboClientConfig implements ConfigFile {
    private String applicationName;
    private String registryAddress;
    private String protocolName;
    private String protocolSerialization;
    private int protocolPort;
    private int consumerTimeout;
    private String annotationPackage;

    @DisconfFileItem(name = "client.application.name")
    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @DisconfFileItem(name = "client.registry.address")
    public String getRegistryAddress() {
        return this.registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    @DisconfFileItem(name = "client.protocol.name")
    public String getProtocolName() {
        return this.protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    @DisconfFileItem(name = "client.protocol.serialization")
    public String getProtocolSerialization() {
        return this.protocolSerialization;
    }

    public void setProtocolSerialization(String protocolSerialization) {
        this.protocolSerialization = protocolSerialization;
    }

    @DisconfFileItem(name = "client.protocol.port")
    public int getProtocolPort() {
        return this.protocolPort;
    }

    public void setProtocolPort(int protocolPort) {
        this.protocolPort = protocolPort;
    }

    @DisconfFileItem(name = "client.consumer.timeout")
    public long getConsumerTimeout() {
        return this.consumerTimeout;
    }

    public void setConsumerTimeout(int consumerTimeout) {
        this.consumerTimeout = consumerTimeout;
    }

    @DisconfFileItem(name = "client.annotation.package")
    public String getAnnotationPackage() {
        return this.annotationPackage;
    }

    public void setAnnotationPackage(String annotationPackage) {
        this.annotationPackage = annotationPackage;
    }

    public String toString() {
        return "DubboClientConfig [applicationName=" + this.applicationName + ", registryAddress="
            + this.registryAddress + ", protocolName=" + this.protocolName + ", protocolSerialization="
            + this.protocolSerialization + ", protocolPort=" + this.protocolPort + ", consumerTimeout="
            + this.consumerTimeout + ", annotationPackage=" + this.annotationPackage + "]";
    }
}