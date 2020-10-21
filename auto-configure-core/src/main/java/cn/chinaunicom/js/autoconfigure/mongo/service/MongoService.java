package cn.chinaunicom.js.autoconfigure.mongo.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * mongodb数据库操作类
 * 
 * @author chenkexiao
 * @date 2019/03/22
 */
public class MongoService {
    private Map<String, Jongo> jongos;
    private Jongo jongo;
    private MongoCollection defultCollection;

    /**
     * 根据数据库名称获取mongo客户端
     * 
     * @param databseName
     * @return
     */
    public Jongo getJongoByDataBase(String databseName) {
        return this.getJongos().get(databseName);
    }

    /**
     * 获取缺省mongo客户端
     * 
     * @param databseName
     * @return
     */
    public Jongo getDefaultJongo() {
        return this.getJongo();
    }

    /**
     * 获取缺省Collection
     * 
     * @return
     */
    public MongoCollection getDefaultCollection() {
        return this.getDefaultCollection();
    }

    /**
     * 根据数据库.集合名称，获取集合对象，参数格式【database.collections】
     * 
     * @param databaseDotCollectionsName
     * @return
     */
    public MongoCollection getCollection(String databaseDotCollectionsName) {
        if (StringUtils.isBlank(databaseDotCollectionsName) || StringUtils.isEmpty(databaseDotCollectionsName)) {
            throw new RuntimeException("MongoService.getCollection failed: parameter can not empty.");
        }
        if (databaseDotCollectionsName.indexOf(".") < 0) {
            throw new RuntimeException(
                "MongoService.getCollection failed: parameter must be [database.collections] forms.");
        }
        String dbName = databaseDotCollectionsName.split("\\.")[0];
        String collectionName = databaseDotCollectionsName.split("\\.")[1];
        MongoCollection collection = this.getJongos().get(dbName).getCollection(collectionName);
        return collection;
    }

    public Map<String, Jongo> getJongos() {
        return jongos;
    }

    public void setJongos(Map<String, Jongo> jongos) {
        this.jongos = jongos;
    }

    public Jongo getJongo() {
        return jongo;
    }

    public void setJongo(Jongo jongo) {
        this.jongo = jongo;
    }

    public MongoCollection getDefultCollection() {
        return defultCollection;
    }

    public void setDefultCollection(MongoCollection defultCollection) {
        this.defultCollection = defultCollection;
    }

}
