package cn.chinaunicom.js.autoconfigure.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.dubbo.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.io.FileUtil;

public class DubboIpsUtils {
    public static Logger logger = LoggerFactory.getLogger(DubboIpsUtils.class);

    /**
     * 通过配置文件设置dubbo服务的绑定ip地址段
     * 
     * @return
     */
    public static Set<String> getDubboIps(String filePath) {
        logger.info("DubboIpsUtils.getDubboIps start...");
        Set<String> allowIps = new HashSet<String>();
        allowIps.addAll(FileUtil.readUtf8Lines(filePath));
        logger.info("DubboIpsUtils.getDubboIps end allowIps:{}", allowIps);
        return allowIps;
    }

    private static List<String> getAllLocalIps() {
        List<String> ips = new ArrayList<>(16);
        Enumeration<NetworkInterface> netInterfaces;
        try {
            // 拿到所有网卡
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            // 遍历每个网卡，拿到ip
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(':') == -1) {
                        ips.add(ip.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
        }
        return ips;
    }

    public static void setDubboSystemProperties(String filePath) {
        Set<String> allowIps = DubboIpsUtils.getDubboIps(filePath);
        // 获取指定网段的内网ip地址，设置为dubbo服务提供者的暴露地址
        List<String> localIps = DubboIpsUtils.getAllLocalIps();
        logger.info("DubboIpsUtils.setDubboSystemProperties allowIps:{}", allowIps);
        logger.info("DubboIpsUtils.setDubboSystemProperties localIps:{}", localIps);
        for (String localIp : localIps) {
            for (String allow : allowIps) {
                if (localIp.startsWith(allow)) {
                    System.setProperty(Constants.DUBBO_IP_TO_REGISTRY, localIp);
                    logger.info("DubboIpsUtils.setDubboSystemProperties registed ip:{}", localIp);
                }
            }
        }
    }
}
