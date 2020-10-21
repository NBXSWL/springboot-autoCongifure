package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;

/**
 * MyBatis SqlSession 配置信息
 * 
 * @author chenkexiao 项目配置信息文件托管(仅在项目启动时，下载到classpath路径下)
 */
@Service
@DisconfFile(filename = "MyBatisJdbc.properties")
public class MyBatisJdbcConfig implements ConfigFile {
    private String typeAliasesPackage;
    private String typeAliasesSuperType;
    private String mapperLocations;
    private String configLocation;
    /**
     * MyBatis的DAO类接口扫描路径，多个之间以逗号分隔
     */
    private String basePackage;

    /**
     * 数据源驱动类可不写，Druid默认会自动根据URL识别DriverClass
     */
    private String driverClass;

    /**
     * 数据库jdbc连接url
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String userName;

    /**
     * 数据库用户密码
     */
    private String password;

    @DisconfFileItem(name = "sqlSession.typeAliasesPackage")
    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    @DisconfFileItem(name = "sqlSession.typeAliasesSuperType")
    public String getTypeAliasesSuperType() {
        return typeAliasesSuperType;
    }

    public void setTypeAliasesSuperType(String typeAliasesSuperType) {
        this.typeAliasesSuperType = typeAliasesSuperType;
    }

    @DisconfFileItem(name = "sqlSession.mapperLocations")
    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    @DisconfFileItem(name = "sqlSession.configLocation")
    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    @DisconfFileItem(name = "mapperscanner.basePackage")
    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @DisconfFileItem(name = "mysql.jdbc.driverClass")
    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @DisconfFileItem(name = "mysql.jdbc.url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DisconfFileItem(name = "mysql.jdbc.userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DisconfFileItem(name = "mysql.jdbc.password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "MyBatisJdbcConfig [typeAliasesPackage=" + typeAliasesPackage + ", typeAliasesSuperType="
            + typeAliasesSuperType + ", mapperLocations=" + mapperLocations + ", configLocation=" + configLocation
            + ", basePackage=" + basePackage + ", driverClass=" + driverClass + ", url=" + url + ", userName="
            + userName + ", password=" + password + "]";
    }

}
