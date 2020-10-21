package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.IpAndPort;
import cn.chinaunicom.js.autoconfigure.util.GsonUtils;

/**
 * 
 * @author chenkexiao 项目配置信息文件托管(仅在项目启动时，下载到classpath路径下)
 */
@Service
@DisconfFile(filename = "MongoClient.json")
public class MongoClientConfig implements ConfigFile {
    private List<IpAndPort> ipAndPortList;
    private String userName;
    private String password;
    private String database;
    private String collection;

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

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "MongoClientConfig [ipAndPortList=" + ipAndPortList + ", userName=" + userName + ", password=" + password
            + ", database=" + database + ", collection=" + collection + "]";
    }

    public static void main(String[] args) {
        System.out.println(GsonUtils.gsonString(new MongoClientConfig()));
    }
}
