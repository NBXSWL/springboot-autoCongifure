package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * MyBatis全局配置
 * 
 * @author chenkexiao
 * @date 2019/03/19
 */
@Service
@DisconfFile(filename = "MyBatisConfig.properties", app = "base")
public class MyBatisConfig implements ConfigFile {
    /** 使全局的映射器启用或禁用缓存。 */
    private boolean cacheEnabled;

    /** 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。 */
    private boolean lazyLoadingEnabled;

    /** 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。 */
    private boolean aggressiveLazyLoading;

    /** 是否允许单条sql 返回多个数据集 (取决于驱动的兼容性) default:true */
    private boolean multipleResultSetsEnabled;

    /** 是否可以使用列的别名 (取决于驱动的兼容性) default:true */
    private boolean useColumnLabel;

    /** 允许JDBC 生成主键。需要驱动器支持。如果设为了true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。 default:false */
    private boolean useGeneratedKeys;

    /** 指定 MyBatis 如何自动映射 数据基表的列 NONE：不隐射 PARTIAL:部分 FULL:全部 */
    private String autoMappingBehavior;

    /** 这是默认的执行类型 （SIMPLE: 简单； REUSE: 执行器可能重复使用prepared statements语句；BATCH: 执行器可以重复执行语句和批量更新） */
    private String defaultExecutorType;

    /** 使用驼峰命名法转换字段。 */
    private boolean mapUnderscoreToCamelCase;

    /** 设置本地缓存范围 session:就会有数据的共享 statement:语句范围 (这样就不会有数据的共享 ) defalut:session */
    private String localCacheScope;

    /** 设置但JDBC类型为空时,某些驱动程序 要指定值,default:OTHER，插入空值时不需要指定类型 */
    private String jdbcTypeForNull;
    /** private String logImpl" value="LOG4J2" /> */
    private String logImpl;

    @DisconfFileItem(name = "base.mybatis.configuration.cacheEnabled")
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.lazyLoadingEnabled")
    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.aggressiveLazyLoading")
    public boolean isAggressiveLazyLoading() {
        return aggressiveLazyLoading;
    }

    public void setAggressiveLazyLoading(boolean aggressiveLazyLoading) {
        this.aggressiveLazyLoading = aggressiveLazyLoading;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.multipleResultSetsEnabled")
    public boolean isMultipleResultSetsEnabled() {
        return multipleResultSetsEnabled;
    }

    public void setMultipleResultSetsEnabled(boolean multipleResultSetsEnabled) {
        this.multipleResultSetsEnabled = multipleResultSetsEnabled;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.useColumnLabel")
    public boolean isUseColumnLabel() {
        return useColumnLabel;
    }

    public void setUseColumnLabel(boolean useColumnLabel) {
        this.useColumnLabel = useColumnLabel;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.useGeneratedKeys")
    public boolean isUseGeneratedKeys() {
        return useGeneratedKeys;
    }

    public void setUseGeneratedKeys(boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.autoMappingBehavior")
    public String getAutoMappingBehavior() {
        return autoMappingBehavior;
    }

    public void setAutoMappingBehavior(String autoMappingBehavior) {
        this.autoMappingBehavior = autoMappingBehavior;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.defaultExecutorType")
    public String getDefaultExecutorType() {
        return defaultExecutorType;
    }

    public void setDefaultExecutorType(String defaultExecutorType) {
        this.defaultExecutorType = defaultExecutorType;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.mapUnderscoreToCamelCase")
    public boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.localCacheScope")
    public String getLocalCacheScope() {
        return localCacheScope;
    }

    public void setLocalCacheScope(String localCacheScope) {
        this.localCacheScope = localCacheScope;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.jdbcTypeForNull")
    public String getJdbcTypeForNull() {
        return jdbcTypeForNull;
    }

    public void setJdbcTypeForNull(String jdbcTypeForNull) {
        this.jdbcTypeForNull = jdbcTypeForNull;
    }

    @DisconfFileItem(name = "base.mybatis.configuration.logImpl")
    public String getLogImpl() {
        return logImpl;
    }

    public void setLogImpl(String logImpl) {
        this.logImpl = logImpl;
    }

    @Override
    public String toString() {
        return "MyBatisConfigurationConfig [cacheEnabled=" + cacheEnabled + ", lazyLoadingEnabled=" + lazyLoadingEnabled
            + ", aggressiveLazyLoading=" + aggressiveLazyLoading + ", multipleResultSetsEnabled="
            + multipleResultSetsEnabled + ", useColumnLabel=" + useColumnLabel + ", useGeneratedKeys="
            + useGeneratedKeys + ", autoMappingBehavior=" + autoMappingBehavior + ", defaultExecutorType="
            + defaultExecutorType + ", mapUnderscoreToCamelCase=" + mapUnderscoreToCamelCase + ", localCacheScope="
            + localCacheScope + ", jdbcTypeForNull=" + jdbcTypeForNull + ", logImpl=" + logImpl + "]";
    }

}
