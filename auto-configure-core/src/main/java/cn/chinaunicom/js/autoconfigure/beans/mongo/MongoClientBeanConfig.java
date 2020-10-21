package cn.chinaunicom.js.autoconfigure.beans.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jongo.Jongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.disconf.entity.base.MongoClientOptionsConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.current.MongoClientConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.IpAndPort;
import cn.chinaunicom.js.autoconfigure.mongo.service.MongoService;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;

/**
 * MongoDb客户端bean配置
 * 
 * @author chenkexiao
 * @date 2019/03/20
 */
@Configuration
@ConditionalFeature(featureName = {"Jongo"})
@ConditionalOnClass({MongoClient.class, MongoCollection.class, MongoDatabase.class})
public class MongoClientBeanConfig {
    @Bean
    @ConditionalOnClass({MongoClient.class})
    public MongoClient mongoClient() {
        MongoClient mongoClient = null;
        MongoClientOptionsConfig mongoClientOptions = ConfigBeanUtils.getBean(MongoClientOptionsConfig.class);
        MongoClientConfig mongoClientConfig = ConfigBeanUtils.getBean(MongoClientConfig.class);

        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.minConnectionsPerHost(mongoClientOptions.getMinConnectionsPerHost());
        build.threadsAllowedToBlockForConnectionMultiplier(
            mongoClientOptions.getThreadsAllowedToBlockForConnectionMultiplier());
        build.connectTimeout(mongoClientOptions.getConnectTimeout());
        build.maxWaitTime(mongoClientOptions.getMaxWaitTime());
        build.socketKeepAlive(mongoClientOptions.isSocketKeepAlive());
        build.socketTimeout(mongoClientOptions.getSocketTimeout());
        build.maxConnectionIdleTime(mongoClientOptions.getMaxConnectionIdleTime());
        build.maxConnectionLifeTime(mongoClientOptions.getMaxConnectionLifeTime());
        build.heartbeatSocketTimeout(mongoClientOptions.getHeartbeatSocketTimeout());
        build.heartbeatConnectTimeout(mongoClientOptions.getHeartbeatConnectTimeout());
        build.minHeartbeatFrequency(mongoClientOptions.getMinHeartbeatFrequency());
        build.heartbeatFrequency(mongoClientOptions.getHeartbeatFrequency());

        MongoClientOptions options = build.build();

        try {
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            for (IpAndPort ipAndPort : mongoClientConfig.getIpAndPortList()) {
                ServerAddress serverAddress = new ServerAddress(ipAndPort.getIp(), ipAndPort.getPort());
                addrs.add(serverAddress);
            }
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            if (StringUtils.isNotEmpty(mongoClientConfig.getUserName())
                && StringUtils.isNotEmpty(mongoClientConfig.getPassword())) {
                MongoCredential credential = MongoCredential.createScramSha1Credential(mongoClientConfig.getUserName(),
                    mongoClientConfig.getDatabase(), mongoClientConfig.getPassword().toCharArray());
                credentials.add(credential);
                mongoClient = new MongoClient(addrs, credentials, options);
            } else {
                mongoClient = new MongoClient(addrs, options);
            }
        } catch (Exception e) {
            throw new RuntimeException("Init MongoClient failed:method mongoClient() throws an Exception", e);
        }
        return mongoClient;
    }

    @Bean
    @ConditionalOnClass({MongoDatabase.class})
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        String[] dataBases = getDatabaseNames();
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBases[0]);
        return mongoDatabase;
    }

    // @Bean
    // @ConditionalOnClass({MongoCollection.class})
    // public MongoCollection<Document> mongoCollection(MongoDatabase mongoDatabase)
    // {
    // MongoClientConfig mongoClientConfig =
    // ConfigBeanUtils.getBean(MongoClientConfig.class);
    // MongoCollection<Document> mongoCollection =
    // mongoDatabase.getCollection(mongoClientConfig.getCollection());
    // return mongoCollection;
    // }

    @SuppressWarnings("deprecation")
    @Bean
    @ConditionalOnClass({DB.class})
    public DB db() {
        String[] dataBases = getDatabaseNames();
        DB db = mongoClient().getDB(dataBases[0]);
        return db;
    }

    @Bean
    @ConditionalOnClass({Jongo.class})
    public Jongo jongo() {
        Jongo jongo = new Jongo(db());
        return jongo;
    }

    @Bean
    @ConditionalOnClass({org.jongo.MongoCollection.class})
    public org.jongo.MongoCollection defultCollection() {
        MongoClientConfig mongoClientConfig = ConfigBeanUtils.getBean(MongoClientConfig.class);
        org.jongo.MongoCollection mongoCollection = jongo().getCollection(mongoClientConfig.getCollection());
        return mongoCollection;
    }

    @SuppressWarnings("deprecation")
    @Bean
    @ConditionalOnClass({DB.class})
    public Map<String, DB> dbs() {
        String[] dbNames = getDatabaseNames();
        Map<String, DB> dbs = new HashMap<>();
        for (String dbName : dbNames) {
            DB db = mongoClient().getDB(dbName);
            dbs.put(dbName, db);
        }
        return dbs;
    }

    /**
     * 从配置文件中获取配置的数据库名字，多个数据库名字之间以逗号分隔
     * 
     * @return
     */
    private String[] getDatabaseNames() {
        MongoClientConfig mongoClientConfig = ConfigBeanUtils.getBean(MongoClientConfig.class);
        String dataBases = mongoClientConfig.getDatabase();
        if (StringUtils.isBlank(dataBases) || StringUtils.isEmpty(dataBases)) {
            throw new RuntimeException("getDatabaseNames failed:Please config database name separated by commas");
        }
        String[] dbNames = dataBases.split(",");
        return dbNames;
    }

    @Bean
    @ConditionalOnClass({Jongo.class})
    public Map<String, Jongo> jongos() {
        Map<String, Jongo> jongos = new HashMap<>();
        MongoClientConfig mongoClientConfig = ConfigBeanUtils.getBean(MongoClientConfig.class);
        String dataBases = mongoClientConfig.getDatabase();
        if (StringUtils.isBlank(dataBases) || StringUtils.isEmpty(dataBases)) {
            throw new RuntimeException("Init dbs failed:Please config database name separated by commas");
        }
        String[] dbNames = dataBases.split(",");
        for (String dbName : dbNames) {
            Jongo jongo = new Jongo(dbs().get(dbName));
            jongos.put(dbName, jongo);
        }

        return jongos;
    }

    @Bean
    @ConditionalOnClass({Jongo.class, org.jongo.MongoCollection.class})
    public MongoService mongoService() {
        MongoService mongoService = new MongoService();
        mongoService.setJongo(jongo());
        mongoService.setJongos(jongos());
        mongoService.setDefultCollection(defultCollection());
        return mongoService;
    }

}
