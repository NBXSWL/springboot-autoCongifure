package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * 数据源配置信息
 * 
 * @author chenkexiao
 * @date 2019/03/15
 */
@Service
@DisconfFile(filename = "DataSource.properties", app = "base")
public class DataSourceConfig implements ConfigFile {

    /**
     * 连接池初始化大小、最小、最大
     */
    private int initialSize;
    /**
     * 连接池最大连接数
     */
    private int maxActive;
    /**
     * 线程池最小连接数
     */
    private int minIdle;
    /**
     * 指定每个连接上PSCache的大小（Oracle使用）
     */
    private int maxPoolPreparedStatementPerConnectionSize;

    /**
     * 获取连接等待超时的时间
     */
    private long maxWait;

    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    private long timeBetweenEvictionRunsMillis;

    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    private long minEvictableIdleTimeMillis;

    /**
     * 配置一个连接在池中最大生存的时间，单位是毫秒
     */
    private long maxEvictableIdleTimeMillis;

    /**
     * 是否打开PSCache
     */
    private boolean poolPreparedStatements;

    /**
     * 关闭、获取、空闲时，测试连接有效性
     */
    private boolean testOnReturn;
    private boolean testOnBorrow;
    private boolean testWhileIdle;

    /**
     * 测试用sql语句
     */
    private String validationQuery;

    /**
     * 配置监控统计拦截的filters
     */
    private String filters;

    /**
     * 物理连接初始化的时候执行的sql，设置一个sql命令，用于设置数据库字符编码为：utf8mb4
     */
    private String connectionInitSqls;

    /**
     * 数据库类型
     */
    private String dbType;

    @DisconfFileItem(name = "base.jdbc.pool.initialSize")
    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    @DisconfFileItem(name = "base.jdbc.pool.maxActive")
    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    @DisconfFileItem(name = "base.jdbc.pool.minIdle")
    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    @DisconfFileItem(name = "base.jdbc.pool.maxWait")
    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    @DisconfFileItem(name = "base.jdbc.pool.timeBetweenEvictionRunsMillis")
    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    @DisconfFileItem(name = "base.jdbc.pool.minEvictableIdleTimeMillis")
    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    @DisconfFileItem(name = "base.jdbc.pool.maxEvictableIdleTimeMillis")
    public long getMaxEvictableIdleTimeMillis() {
        return maxEvictableIdleTimeMillis;
    }

    public void setMaxEvictableIdleTimeMillis(long maxEvictableIdleTimeMillis) {
        this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis;
    }

    @DisconfFileItem(name = "base.jdbc.pool.validationQuery")
    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    @DisconfFileItem(name = "base.jdbc.pool.testWhileIdle")
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    @DisconfFileItem(name = "base.jdbc.pool.testOnBorrow")
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    @DisconfFileItem(name = "base.jdbc.pool.testOnReturn")
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    @DisconfFileItem(name = "base.jdbc.pool.poolPreparedStatements")
    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    @DisconfFileItem(name = "base.jdbc.pool.maxPoolPreparedStatementPerConnectionSize")
    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    @DisconfFileItem(name = "base.jdbc.pool.filters")
    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    @DisconfFileItem(name = "base.jdbc.pool.connectionInitSqls")
    public String getConnectionInitSqls() {
        return connectionInitSqls;
    }

    public void setConnectionInitSqls(String connectionInitSqls) {
        this.connectionInitSqls = connectionInitSqls;
    }

    @DisconfFileItem(name = "base.jdbc.pool.dbType")
    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public String toString() {
        return "DataSourceConfig{" +
                "initialSize=" + initialSize +
                ", maxActive=" + maxActive +
                ", minIdle=" + minIdle +
                ", maxPoolPreparedStatementPerConnectionSize=" + maxPoolPreparedStatementPerConnectionSize +
                ", maxWait=" + maxWait +
                ", timeBetweenEvictionRunsMillis=" + timeBetweenEvictionRunsMillis +
                ", minEvictableIdleTimeMillis=" + minEvictableIdleTimeMillis +
                ", maxEvictableIdleTimeMillis=" + maxEvictableIdleTimeMillis +
                ", poolPreparedStatements=" + poolPreparedStatements +
                ", testOnReturn=" + testOnReturn +
                ", testOnBorrow=" + testOnBorrow +
                ", testWhileIdle=" + testWhileIdle +
                ", validationQuery='" + validationQuery + '\'' +
                ", filters='" + filters + '\'' +
                ", connectionInitSqls='" + connectionInitSqls + '\'' +
                ", dbType='" + dbType + '\'' +
                '}';
    }
}
