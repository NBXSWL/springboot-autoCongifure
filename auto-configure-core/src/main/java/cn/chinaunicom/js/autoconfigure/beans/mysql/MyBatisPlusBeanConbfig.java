package cn.chinaunicom.js.autoconfigure.beans.mysql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.disconf.entity.base.DataSourceConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.base.MyBatisConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.base.MyBatisPluginsConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.current.MyBatisJdbcConfig;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;

/**
 * Druid数据源配置
 * 
 * @author chenkexiao
 * @date 2019/03/15
 */
@Configuration
@EnableTransactionManagement
@ConditionalFeature(featureName = {"MyBatis-plus"})
@ConditionalOnClass({DruidDataSource.class, MybatisSqlSessionFactoryBean.class, MapperScannerConfigurer.class})
public class MyBatisPlusBeanConbfig {

    @Bean
    @ConditionalOnClass({MybatisSqlSessionFactoryBean.class})
    public MybatisSqlSessionFactoryBean sqlSessionFactory() {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        MyBatisJdbcConfig myBatisJdbcConfig = ConfigBeanUtils.getBean(MyBatisJdbcConfig.class);
        sqlSessionFactory.setDataSource(dataSource());
        // 这个参数配置不对，会导致：java.lang.ArrayStoreException: sun.reflect.annotation.TypeNotPresentExceptionProxy
        if (StringUtils.isNoneEmpty(myBatisJdbcConfig.getTypeAliasesPackage())) {
            sqlSessionFactory.setTypeAliasesPackage(myBatisJdbcConfig.getTypeAliasesPackage());
        }
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            if (StringUtils.isNoneEmpty(myBatisJdbcConfig.getTypeAliasesSuperType())) {
                Class<?> typeAliasesSuperType = Class.forName(myBatisJdbcConfig.getTypeAliasesSuperType());
                sqlSessionFactory.setTypeAliasesSuperType(typeAliasesSuperType);
            }
            if (StringUtils.isNoneEmpty(myBatisJdbcConfig.getMapperLocations())) {
                sqlSessionFactory.setMapperLocations(resolver.getResources(myBatisJdbcConfig.getMapperLocations()));
            }
            // MyBatis插件信息
            MyBatisPluginsConfig myBatisPlugins = ConfigBeanUtils.getBean(MyBatisPluginsConfig.class);
            Interceptor[] plugins = new Interceptor[myBatisPlugins.size()];
            int i = 0;
            for (Entry<String, Properties> entry : myBatisPlugins.entrySet()) {
                String className = entry.getKey();
                Properties properties = entry.getValue();
                if (StringUtils.isNoneEmpty(className)) {
                    Interceptor interceptorInstance = (Interceptor)Class.forName(className).newInstance();
                    interceptorInstance.setProperties(properties);
                    plugins[i] = interceptorInstance;
                }
                i++;
            }
            // 插件信息通过configuration()配置
            // sqlSessionFactory.setPlugins(plugins);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Init SqlSessionFactoryBean failed:method sqlSessionFactory() throws an ClassNotFoundException", e);
        } catch (IOException e) {
            throw new RuntimeException(
                "Init SqlSessionFactoryBean failed:method sqlSessionFactory() throws an IOException", e);
        } catch (InstantiationException e) {
            throw new RuntimeException(
                "Init SqlSessionFactoryBean failed:method sqlSessionFactory() throws an InstantiationException", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                "Init SqlSessionFactoryBean failed:method sqlSessionFactory() throws an IllegalAccessException", e);
        }
        if (StringUtils.isNoneEmpty(myBatisJdbcConfig.getConfigLocation())) {
            sqlSessionFactory.setConfigLocation(resolver.getResource(myBatisJdbcConfig.getConfigLocation()));
        }
        sqlSessionFactory.setConfiguration(configuration());
        sqlSessionFactory.setGlobalConfig(globalConfig());
        return sqlSessionFactory;
    }

    @Bean
    @ConditionalOnClass({GlobalConfig.class})
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig());
        LogicSqlInjector injector = new LogicSqlInjector();
        globalConfig.setSqlInjector(injector);
        return globalConfig;
    }

    @Bean
    @ConditionalOnClass({DbConfig.class})
    public DbConfig dbConfig() {
        DbConfig dbConfig = new DbConfig();
        dbConfig.setTableUnderline(true);
        dbConfig.setCapitalMode(true);
        dbConfig.setLogicDeleteValue("-1");
        dbConfig.setLogicNotDeleteValue("1");
        return dbConfig;
    }

    @Bean
    @ConditionalOnClass({MybatisConfiguration.class})
    public MybatisConfiguration configuration() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        // MyBatis全局配置信息
        MyBatisConfig myBatisConfig = ConfigBeanUtils.getBean(MyBatisConfig.class);
        // MyBatis插件信息
        MyBatisPluginsConfig myBatisPlugins = ConfigBeanUtils.getBean(MyBatisPluginsConfig.class);
        configuration.setCacheEnabled(myBatisConfig.isCacheEnabled());
        configuration.setLazyLoadingEnabled(myBatisConfig.isLazyLoadingEnabled());
        configuration.setAggressiveLazyLoading(myBatisConfig.isAggressiveLazyLoading());
        configuration.setMultipleResultSetsEnabled(myBatisConfig.isMultipleResultSetsEnabled());
        configuration.setUseColumnLabel(myBatisConfig.isUseColumnLabel());
        configuration.setUseGeneratedKeys(myBatisConfig.isUseGeneratedKeys());
        configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(myBatisConfig.getAutoMappingBehavior()));
        configuration.setDefaultExecutorType(ExecutorType.valueOf(myBatisConfig.getDefaultExecutorType()));
        configuration.setMapUnderscoreToCamelCase(myBatisConfig.isMapUnderscoreToCamelCase());
        configuration.setLocalCacheScope(LocalCacheScope.valueOf(myBatisConfig.getLocalCacheScope()));
        configuration.setJdbcTypeForNull(JdbcType.valueOf(myBatisConfig.getJdbcTypeForNull()));
        try {
            TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
            configuration.setLogImpl(typeAliasRegistry.resolveAlias(myBatisConfig.getLogImpl()));
            for (Entry<String, Properties> entry : myBatisPlugins.entrySet()) {
                String className = entry.getKey();
                Properties properties = entry.getValue();
                if (StringUtils.isNoneEmpty(className)) {
                    Interceptor interceptorInstance = (Interceptor)Class.forName(className).newInstance();
                    interceptorInstance.setProperties(properties);
                    configuration.addInterceptor(interceptorInstance);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Init org.apache.ibatis.session.Configuration failed:method configuration() throws a ClassNotFoundException",
                e);
        } catch (InstantiationException e) {
            throw new RuntimeException(
                "Init org.apache.ibatis.session.Configuration failed:method configuration() throws an InstantiationException",
                e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                "Init org.apache.ibatis.session.Configuration failed:method configuration() throws an IllegalAccessException",
                e);
        }
        return configuration;
    }

    @Bean
    @ConditionalOnClass({MapperScannerConfigurer.class})
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        MyBatisJdbcConfig myBatisJdbcConfig = ConfigBeanUtils.getBean(MyBatisJdbcConfig.class);
        mapperScannerConfigurer.setBasePackage(myBatisJdbcConfig.getBasePackage());
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean
    @ConditionalOnClass({DataSourceTransactionManager.class})
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    @ConditionalOnClass({DruidDataSource.class})
    public DruidDataSource dataSource() {
        // 数据源配置信息
        DataSourceConfig dataSourceConfig = ConfigBeanUtils.getBean(DataSourceConfig.class);
        // 数据库
        MyBatisJdbcConfig myBatisJdbcConfig = ConfigBeanUtils.getBean(MyBatisJdbcConfig.class);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setConnectionInitSqls(Arrays.asList(dataSourceConfig.getConnectionInitSqls().split(",")));
        dataSource.setDbType(dataSourceConfig.getDbType());
        try {
            dataSource.setFilters(dataSourceConfig.getFilters());
        } catch (SQLException e) {
            throw new RuntimeException("Init DruidDataSource failed:method setFilters() throws an exceptions", e);
        }
        dataSource.setInitialSize(dataSourceConfig.getInitialSize());
        dataSource.setMaxActive(dataSourceConfig.getMaxActive());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(
            dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setMaxWait(dataSourceConfig.getMaxWait());
        dataSource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
        dataSource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
        dataSource.setMaxEvictableIdleTimeMillis(dataSourceConfig.getMaxEvictableIdleTimeMillis());
        dataSource.setMinIdle(dataSourceConfig.getMinIdle());
        dataSource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
        dataSource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
        dataSource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
        dataSource.setValidationQuery(dataSourceConfig.getValidationQuery());
        // List<Filter> proxyFilters = new ArrayList<>();
        // proxyFilters.add(logFilter);
        dataSource.setProxyFilters(proxyFilters());
        dataSource.setUrl(myBatisJdbcConfig.getUrl());
        dataSource.setDriverClassName(myBatisJdbcConfig.getDriverClass());
        dataSource.setUsername(myBatisJdbcConfig.getUserName());
        dataSource.setPassword(myBatisJdbcConfig.getPassword());
        // dataSource.setAccessToUnderlyingConnectionAllowed(boolean);
        // dataSource.setAsyncCloseConnectionEnable(boolean);
        // dataSource.setBreakAfterAcquireFailure(boolean);
        // dataSource.setClearFiltersEnable(boolean);
        // dataSource.setConnectionErrorRetryAttempts(int);
        // dataSource.setConnectionProperties(String);
        // dataSource.setConnectProperties(Properties);
        // dataSource.setCreateScheduler(ScheduledExecutorService);
        // dataSource.setDefaultAutoCommit(boolean);
        // dataSource.setDefaultCatalog(String);
        // dataSource.setDefaultReadOnly(Boolean);
        // dataSource.setDefaultTransactionIsolation(Integer);
        // dataSource.setDestroyScheduler(ScheduledExecutorService);
        // dataSource.setDriver(Driver);
        // dataSource.setDriverClassLoader(ClassLoader);
        // dataSource.setDupCloseLogEnable(boolean);
        // dataSource.setExceptionSorter(ExceptionSorter);
        // dataSource.setExceptionSorter(String);
        // dataSource.setExceptionSorterClassName(String);
        // dataSource.setLogAbandoned(boolean);
        // dataSource.setLoginTimeout(int);
        // dataSource.setLogWriter(PrintWriter);
        // dataSource.setMaxCreateTaskCount(int);
        // dataSource.setMaxOpenPreparedStatements(int);
        // dataSource.setMaxWaitThreadCount(int);
        // dataSource.setName(String);
        // dataSource.setNotFullTimeoutRetryCount(int);
        // dataSource.setNumTestsPerEvictionRun(int);
        // dataSource.setObjectName(ObjectName);
        // dataSource.setOracle(boolean);
        // dataSource.setPasswordCallback(PasswordCallback);
        // dataSource.setPasswordCallbackClassName(String);
        // dataSource.setQueryTimeout(int);
        // dataSource.setRemoveAbandoned(boolean);
        // dataSource.setRemoveAbandonedTimeout(int);
        // dataSource.setRemoveAbandonedTimeoutMillis(long);
        // dataSource.setSharePreparedStatements(boolean);
        // dataSource.setStatLogger(DruidDataSourceStatLogger);
        // dataSource.setStatLoggerClassName(String);
        // dataSource.setTimeBetweenConnectErrorMillis(long);
        // dataSource.setTimeBetweenEvictionRunsMillis(long);
        // dataSource.setTimeBetweenLogStatsMillis(long);
        // dataSource.setTransactionQueryTimeout(int);
        // dataSource.setTransactionThresholdMillis(long);
        // dataSource.setUseLocalSessionState(boolean);
        // dataSource.setUseOracleImplicitCache(boolean);
        // dataSource.setUserCallback(NameCallback);
        // dataSource.setUseUnfairLock(boolean);
        // dataSource.setValidationQueryTimeout(int);
        // dataSource.setValidConnectionChecker(ValidConnectionChecker);
        // dataSource.setValidConnectionCheckerClassName(String);
        return dataSource;
    }

    @Bean
    @ConditionalOnClass({Slf4jLogFilter.class})
    public Slf4jLogFilter logFilter() {
        Slf4jLogFilter logFilter = new Slf4jLogFilter();
        logFilter.setConnectionLogEnabled(false);
        logFilter.setStatementLogEnabled(false);
        logFilter.setResultSetLogEnabled(true);
        logFilter.setStatementExecutableSqlLogEnable(true);
        return logFilter;
    }

    @Bean
    @ConditionalOnClass({Filter.class})
    public List<Filter> proxyFilters() {
        List<Filter> proxyFilters = new ArrayList<>();
        proxyFilters.add(logFilter());
        return proxyFilters;
    }

}
