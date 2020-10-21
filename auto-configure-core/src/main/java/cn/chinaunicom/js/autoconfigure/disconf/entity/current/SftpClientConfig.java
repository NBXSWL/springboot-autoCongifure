package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.IpAndPort;

/**
 * 
 * @author chenkexiao 项目配置信息文件托管(仅在项目启动时，下载到classpath路径下)
 */
@Service
@DisconfFile(filename = "SftpClient.json")
public class SftpClientConfig implements ConfigFile {
    /**
     * sftp的地址和端口号列表
     */
    private List<IpAndPort> ipAndPortList;
    /**
     * sftp的用户名
     */
    private String userName;
    /**
     * sftp的用户密码
     */
    private String password;
    /**
     * sftp的文件上传目录
     */
    private String baseDir;
    /**
     * 上传的文件访问域名
     */
    private String domainName;
    /**
     * 请求上下文路径
     */
    private String contextPath;

    public List<IpAndPort> getIpAndPortList() {
        return ipAndPortList;
    }

    public void setIpAndPortList(List<IpAndPort> ipAndPortList) {
        this.ipAndPortList = ipAndPortList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String toString() {
        return "SftpClientConfig [ipAndPortList=" + ipAndPortList + ", userName=" + userName + ", password=" + password
            + ", baseDir=" + baseDir + ", domainName=" + domainName + ", contextPath=" + contextPath + "]";
    }

}
