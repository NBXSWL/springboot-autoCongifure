package cn.chinaunicom.js.autoconfigure.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import cn.chinaunicom.js.autoconfigure.disconf.entity.current.SftpClientConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.IpAndPort;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;

/**
 * 
 * @ClassName: SFTPUtil
 * @Description: sftp连接工具类
 * @date 2017年5月22日 下午11:17:21
 * @version 1.0.0
 */
public class SftpUtils {
    private transient static Logger log = LoggerFactory.getLogger(SftpUtils.class);

    public SftpUtils() {}

    private static HashMap<IpAndPort, Sftp> CACHE_POOL = new HashMap<>();
    private static HashMap<IpAndPort, Session> SEESION_CACHE = new HashMap<>();

    private static Sftp getSftp(IpAndPort ipAndPort) {
        Sftp sftp = CACHE_POOL.get(ipAndPort);
        Session session = SEESION_CACHE.get(ipAndPort);
        if (session != null && session.isConnected() && sftp != null) {
            return sftp;
        }
        SftpClientConfig sftpClientConfig = ConfigBeanUtils.getBean(SftpClientConfig.class);
        final String userName = sftpClientConfig.getUserName();
        final String password = sftpClientConfig.getPassword();
        final String baseDir = sftpClientConfig.getBaseDir();
        session = JschUtil.getSession(ipAndPort.getIp(), ipAndPort.getPort(), userName, password);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        SEESION_CACHE.put(ipAndPort, session);
        sftp = new Sftp(session);
        if (sftp != null && StringUtils.isNotEmpty(baseDir)) {
            try {
                sftp.mkdir(sftp.home() + baseDir);
            } catch (Exception e) {
                log.error("create dir fails : {}", e.getMessage());
            }
        }
        CACHE_POOL.put(ipAndPort, sftp);
        return sftp;
    }

    public static void closeSftp() {
        for (Entry<IpAndPort, Sftp> entry : CACHE_POOL.entrySet()) {
            if (entry.getValue() != null) {
                try {
                    entry.getValue().close();
                } catch (IOException e) {
                    log.error("close sftp Exception :{}", e);
                }
                CACHE_POOL.entrySet().remove(entry);
            }

        }
    }

    /**
     * 上传单个文件
     * 
     * @param directory
     *            上传到sftp目录
     * @param fileName
     *            要上传的文件名
     * @throws FileNotFoundException
     * @throws SftpException
     * @throws Exception
     */
    public static void uploadFile(String srcFilePath, String fileName) {
        SftpClientConfig sftpClientConfig = ConfigBeanUtils.getBean(SftpClientConfig.class);
        final String baseDir = sftpClientConfig.getBaseDir();
        List<IpAndPort> list = sftpClientConfig.getIpAndPortList();
        for (final IpAndPort ipAndPort : list) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Sftp sftp = getSftp(ipAndPort);
                    if (sftp != null) {
                        // 进入远程目录
                        if (StringUtils.isNotEmpty(baseDir) && sftp.cd(baseDir)) {
                            // 上传本地文件
                            sftp.put(srcFilePath, baseDir + "/" + fileName);
                        } else {
                            sftp.put(srcFilePath, fileName);
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * 上传单个文件 到指定目录
     * 
     * @param srcFilePath
     *            源文件
     * @param fileName
     *            要上传的文件名
     * @param targetDir
     *            目标目录，首尾都不带斜杠
     * @throws FileNotFoundException
     * @throws SftpException
     * @throws Exception
     */
    public static void uploadFile(String srcFilePath, String fileName, String targetDir) {
        SftpClientConfig sftpClientConfig = ConfigBeanUtils.getBean(SftpClientConfig.class);
        final String baseDir = sftpClientConfig.getBaseDir();
        List<IpAndPort> list = sftpClientConfig.getIpAndPortList();
        for (final IpAndPort ipAndPort : list) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Sftp sftp = getSftp(ipAndPort);
                    if (sftp != null) {
                        // 进入远程目录
                        if (StringUtils.isNotEmpty(baseDir) && sftp.cd(baseDir)) {
                            String destDir = baseDir + "/" + targetDir;
                            try {
                                sftp.mkdir(sftp.home() + destDir);
                                sftp.cd(destDir);
                                // 上传本地文件
                                sftp.put(srcFilePath, baseDir + "/" + fileName);
                            } catch (Exception e) {
                                log.error("create dir fails : {}", e.getMessage());
                            }
                        } else {
                            sftp.put(srcFilePath, fileName);
                        }
                    }
                }
            }).start();
        }
    }

    public static String getBaseDir() {
        SftpClientConfig sftpClientConfig = ConfigBeanUtils.getBean(SftpClientConfig.class);
        String baseDir = sftpClientConfig.getBaseDir();
        List<IpAndPort> list = sftpClientConfig.getIpAndPortList();
        IpAndPort ipAndPort = list.get(0);
        Sftp sftp = getSftp(ipAndPort);
        String home = sftp.home();
        if (StringUtils.isNotEmpty(baseDir) && sftp.cd(home + baseDir)) {
            return home + baseDir;
        }
        return home;
    }

    public static String getDomainName() {
        SftpClientConfig sftpClientConfig = ConfigBeanUtils.getBean(SftpClientConfig.class);
        String domainName = sftpClientConfig.getDomainName();
        return domainName;
    }

    public static String getContextPath() {
        SftpClientConfig sftpClientConfig = ConfigBeanUtils.getBean(SftpClientConfig.class);
        String contextPath = sftpClientConfig.getContextPath();
        return contextPath;
    }
}