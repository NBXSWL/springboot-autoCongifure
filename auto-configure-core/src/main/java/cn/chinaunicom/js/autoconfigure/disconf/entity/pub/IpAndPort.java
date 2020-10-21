package cn.chinaunicom.js.autoconfigure.disconf.entity.pub;

/**
 * ip地址和端口号配置
 * 
 * @author chenkexiao
 * @date 2019/03/13
 */
public class IpAndPort {
    private String ip;
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "RedisIpPort [ip=" + ip + ", port=" + port + "]";
    }

}