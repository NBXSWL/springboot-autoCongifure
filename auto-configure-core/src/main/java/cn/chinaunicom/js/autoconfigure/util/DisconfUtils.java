package cn.chinaunicom.js.autoconfigure.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.baidu.disconf.client.common.model.DisconfCenterFile;
import com.baidu.disconf.client.config.ConfigMgr;
import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.client.fetcher.FetcherFactory;
import com.baidu.disconf.client.fetcher.FetcherMgr;
import com.baidu.disconf.client.store.DisconfStoreProcessor;
import com.baidu.disconf.client.store.DisconfStoreProcessorFactory;
import com.baidu.disconf.client.support.utils.StringUtil;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.baidu.disconf.core.common.restful.core.RemoteUrl;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.GlobalConfig;

public class DisconfUtils {
    public static Logger logger = LoggerFactory.getLogger(DisconfUtils.class);
    private static Reflections reflections = null;
    private static Set<String> disableFeature = new HashSet<String>();
    private static Map<String, Map<String, String>> fileContentCache = new HashMap<>();

    /**
     * 通过配置文件设置disconf扫描的配置类包名，多个包名逗号分隔
     * 
     * @return
     */
    public static String getScanPackage() {
        String scanPackage = CurrentConstants.DEFAULT_SCAN_PACAGE;
        Properties prop = getDisconfProp();
        scanPackage = prop.getProperty(CurrentConstants.SCAN_PACAGE);
        return scanPackage;
    }

    public static Reflections getReflections() {
        Object[] packages = DisconfUtils.getScanPackage().split(",");
        if (reflections == null) {
            reflections = new Reflections(packages);
        }
        return reflections;
    }

    /**
     * 通过配置文件设置disconf扫描的配置类包名，多个包名逗号分隔
     * 
     * @return
     */
    public static Set<String> getDisableFeatures() {
        if (disableFeature.size() > 0) {
            return disableFeature;
        }
        try {
            DisClientConfig.getInstance().loadConfig(null);
        } catch (Exception e) {
            logger.error("DisconfUtils.getDisableFeatures Exception", e);
        }
        if (!CollectionUtils.isEmpty(DisClientConfig.getInstance().getDisableFeatures())) {
            disableFeature.addAll(DisClientConfig.getInstance().getDisableFeatures());
        }
        return disableFeature;
    }

    public static String downloadFileFromServerToDir(String fileName) {
        String appVersion = DisClientConfig.getInstance().VERSION;
        String appName = DisClientConfig.getInstance().APP;
        String appEnv = DisClientConfig.getInstance().ENV;
        StringBuilder url = new StringBuilder("/api/config/file?version=");
        url.append(appVersion);
        url.append("&app=");
        url.append(appName);
        url.append("&env=");
        url.append(appEnv);
        url.append("&type=0&key=");
        url.append(fileName);
        try {
            // 下载文件
            FetcherMgr fetcherMgr;
            fetcherMgr = FetcherFactory.getFetcherMgr();
            return fetcherMgr.downloadFileFromServerToDir(url.toString(), fileName,
                DisClientConfig.getInstance().userDefineDownloadDir);
        } catch (Exception e) {
            throw new RuntimeException("download file fail !");
        }
    }

    /**
     * 查询是否已经有记录
     * 
     * @param fileName
     *            配置文件名
     * @return
     */
    public static String getConfigByFile(String fileName) {
        logger.info("DisconfUtils.getConfigByFile begin fileName:{}", fileName);
        if (fileContentCache.containsKey(fileName)) {
            String cache = fileContentCache.get(fileName).get(fileName);
            logger.info("DisconfUtils.getConfigByFile fileName:{},cache:{}", cache);
            return cache;
        }
        DisconfStoreProcessor disconfStoreProcessor = DisconfStoreProcessorFactory.getDisconfStoreFileProcessor();
        DisconfCenterFile disconfCenterFile = (DisconfCenterFile)disconfStoreProcessor.getConfData(fileName);
        String filePath = disconfCenterFile.getFilePath();
        String str = "";
        try {
            str = FileUtils.readFileToString(new File(filePath));
            if (StringUtils.isNotEmpty(str)) {
                Map<String, String> content = new HashMap<>();
                content.put(fileName, str);
                fileContentCache.put(fileName, content);
            }
        } catch (IOException e) {
            logger.error("DisconfUtils.getConfigByFile FileUtils.readFileToString IOException", e);
        }
        logger.info("DisconfUtils.getConfigByFile end file content:{}", str);
        return str;
    }

    /**
     * 用于CustomFiles.properties文件变更时，刷新文件内容缓存（直接删除，服务使用时重新加载文件内容）
     * 
     * @param fileName
     */
    public static void refreshFileContentCache(String fileName) {
        logger.info("DisconfUtils.getCfgByFileKey begin fileName:{}", fileName);
        fileContentCache.remove(fileName);
        getCfgByFileKey(fileName, "");
        logger.info("DisconfUtils.getCfgByFileKey end ");
    }

    public static String getCfgByFileKey(String fileName, String keyName) {
        logger.info("DisconfUtils.getCfgByFileKey begin fileName:{},keyName:{}", fileName, keyName);
        if (fileContentCache.containsKey(fileName)) {
            String cache = "";
            if (StringUtils.isNotEmpty(keyName)) {
                cache = fileContentCache.get(fileName).get(keyName);
            } else {
                if (fileContentCache.get(fileName) != null && fileContentCache.get(fileName).get(fileName) != null) {
                    cache = fileContentCache.get(fileName).get(fileName);
                } else {
                    cache = GsonUtils.gsonString(fileContentCache.get(fileName));
                }
            }
            logger.info("DisconfUtils.getCfgByFileKey fileName:{},cache:{}", cache);
            return cache;
        }
        String result = "";
        String filePath = DisconfUtils.downloadFileFromServerToDir(fileName);
        try {
            result = FileUtils.readFileToString(new File(filePath));
        } catch (IOException e1) {
            logger.error("DisconfUtils.getCfgByFileKey FileUtils.readFileToString IOException", e1);
        }
        if (fileName.endsWith(FileTypeConsts.PROPERTIES_SUFFIX)) {
            try (InputStream inputStream = FileUtils.openInputStream(new File(filePath))) {
                Properties prop = new Properties();
                prop.load(inputStream);
                Map<String, String> content = transPropToMap(prop);
                if (StringUtils.isNotEmpty(keyName)) {
                    result = content.get(keyName);
                } else {
                    result = GsonUtils.gsonString(content);
                }
                fileContentCache.put(fileName, content);
            } catch (IOException e) {
                logger.error("DisconfUtils.getCfgByFileKey FileUtils.openInputStream IOException", e);
            }
            return result;
        }
        if (fileName.endsWith(FileTypeConsts.JSON_SUFFIX)) {
            try {
                Map<String, LinkedTreeMap<String, JsonElement>> map = GsonUtils.gsonToMaps(result);
                if (StringUtils.isNotEmpty(keyName)) {
                    if (map.get(keyName) != null) {
                        result = GsonUtils.gsonString(map.get(keyName));
                    }
                } else {
                    result = GsonUtils.gsonString(map);
                }
                Map<String, String> content = new HashMap<>();
                for (Map.Entry<String, LinkedTreeMap<String, JsonElement>> entry : map.entrySet()) {
                    content.put(entry.getKey(), GsonUtils.gsonString(entry.getValue()));
                }
                fileContentCache.put(fileName, content);
            } catch (Exception e) {
                logger.error("DisconfUtils.getCfgByFileKey FileUtils.readFileToString or GsonUtils.gsonToMaps:{}", e);
            }
            return result;
        }
        Map<String, String> content = new HashMap<>();
        content.put(fileName, result);
        fileContentCache.put(fileName, content);
        logger.info("ConfigTest.getCfgByFile end file content:{}", result);
        return result;
    }

    /**
     * 查询是否已经有记录
     * 
     * @param key
     *            配置项名称
     * @return
     */
    public static String getConfigByKey(String key) {
        logger.info("DisconfUtils.getConfigByKey begin key:{}", key);
        String value = "";
        if (StringUtils.isEmpty(key)) {
            value = GlobalConfig.getKeyValue().toString();
        } else {
            value = GlobalConfig.getConfigByKey(key);
            if (StringUtils.isEmpty(key)) {
                value = (String)DisconfDataGetter.getByItem(key);
            }
        }
        logger.info("DisconfUtils.getConfigByKey begin value:{}", value);
        return value;
    }

    /**
     * 将properties对象转为map对象
     * 
     * @param prop
     * @return
     */
    private static Map<String, String> transPropToMap(Properties prop) {
        Map<String, String> map = new HashMap<>();
        if (prop != null) {
            Iterator<String> it = prop.stringPropertyNames().iterator();
            String key = "";
            while (it.hasNext()) {
                key = it.next();
                map.put(key, prop.getProperty(key));
            }
        }
        return map;
    }

    public static void downloadYmlFile() {
        Properties prop = getDisconfProp();
        String appVersion = prop.getProperty("disconf.version", "DEFAULT_VERSION");
        String appName = prop.getProperty("disconf.app", "base");
        String appEnv = prop.getProperty("disconf.env", "DEFAULT_ENV");
        String hostListStr = prop.getProperty("disconf.conf_server_host", "");
        StringBuilder url = new StringBuilder("/api/config/simple/list?version=");
        url.append(appVersion);
        url.append("&app=");
        url.append(appName);
        url.append("&env=");
        url.append(appEnv);
        // 设置远程地址
        List<String> hostList = StringUtil.parseStringToStringList(hostListStr, ",");
        RemoteUrl remoteUrl = new RemoteUrl(url.toString(), hostList);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            InputStream is = null;
            HttpGet httpGet = new HttpGet(remoteUrl.getUrls().get(0).toString());
            // 执行Get请求，
            response = httpClient.execute(httpGet);
            // 得到响应体
            HttpEntity entity = response.getEntity();
            JsonObject data = new JsonObject();
            if (entity != null) {
                is = entity.getContent();
                // 转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                String str = "";
                while ((str = br.readLine()) != null) {
                    body = str + "\n";
                }
                data = GsonUtils.jsonStringToObject(body);
                JsonArray files = data.get("page").getAsJsonObject().get("result").getAsJsonArray();
                for (JsonElement file : files) {
                    String type = file.getAsJsonObject().get("type").getAsString();
                    String fileName = file.getAsJsonObject().get("name").getAsString();
                    if (type.equals("0") && fileName.toLowerCase().endsWith(".yml")) {
                        // 下载文件
                        StringBuilder downUrl = new StringBuilder("/api/config/file?version=");
                        downUrl.append(appVersion);
                        downUrl.append("&app=");
                        downUrl.append(appName);
                        downUrl.append("&env=");
                        downUrl.append(appEnv);
                        downUrl.append("&type=0&key=");
                        downUrl.append(fileName);
                        ConfigMgr.init();
                        FetcherMgr fetcherMgr = FetcherFactory.getFetcherMgr();
                        fetcherMgr.downloadFileFromServerToClassPath(downUrl.toString(), fileName,
                            DisClientConfig.getInstance().userDefineDownloadDir);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("download file fail !");
        }
    }

    public static String downloadDubboIpFile() {
        logger.info("DisconfUtils.downloadDubboIpFile start...");
        Properties prop = getDisconfProp();
        String appVersion = prop.getProperty("disconf.version", "DEFAULT_VERSION");
        String appName = "base";
        String appEnv = prop.getProperty("disconf.env", "DEFAULT_ENV");
        String hostListStr = prop.getProperty("disconf.conf_server_host", "");
        StringBuilder url = new StringBuilder("/api/config/simple/list?version=");
        url.append(appVersion);
        url.append("&app=");
        url.append(appName);
        url.append("&env=");
        url.append(appEnv);
        // 设置远程地址
        List<String> hostList = StringUtil.parseStringToStringList(hostListStr, ",");
        RemoteUrl remoteUrl = new RemoteUrl(url.toString(), hostList);
        String filePath = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            InputStream is = null;
            HttpGet httpGet = new HttpGet(remoteUrl.getUrls().get(0).toString());
            // 执行Get请求，
            response = httpClient.execute(httpGet);
            // 得到响应体
            HttpEntity entity = response.getEntity();
            JsonObject data = new JsonObject();
            if (entity != null) {
                is = entity.getContent();
                // 转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                String str = "";
                while ((str = br.readLine()) != null) {
                    body = str + "\n";
                }
                data = GsonUtils.jsonStringToObject(body);
                JsonArray files = data.get("page").getAsJsonObject().get("result").getAsJsonArray();
                for (JsonElement file : files) {
                    String type = file.getAsJsonObject().get("type").getAsString();
                    String fileName = file.getAsJsonObject().get("name").getAsString();
                    if (type.equals("0") && fileName.equals(CurrentConstants.DUBBOIPS_CFG_FILE)) {
                        // 下载文件
                        StringBuilder downUrl = new StringBuilder("/api/config/file?version=");
                        downUrl.append(appVersion);
                        downUrl.append("&app=");
                        downUrl.append(appName);
                        downUrl.append("&env=");
                        downUrl.append(appEnv);
                        downUrl.append("&type=0&key=");
                        downUrl.append(fileName);
                        ConfigMgr.init();
                        FetcherMgr fetcherMgr = FetcherFactory.getFetcherMgr();
                        filePath = fetcherMgr.downloadFileFromServerToClassPath(downUrl.toString(), fileName,
                            DisClientConfig.getInstance().userDefineDownloadDir);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("download file fail !");
        }
        logger.info("DisconfUtils.downloadDubboIpFile end...filePth:{}", filePath);
        return filePath;
    }

    /**
     * 通过配置文件设置disconf扫描的配置类包名，多个包名逗号分隔
     * 
     * @return
     */
    public static Properties getDisconfProp() {
        InputStream inputStream = null;
        try {
            inputStream = DisconfUtils.class.getClassLoader().getResourceAsStream(CurrentConstants.DISCONF_CFG_FILE);
            Properties prop = new Properties();
            prop.load(inputStream);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException("disconf.properties file could not be found under the classpath !");
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

    }

    public static List<String> getSysCfgFileList() {
        String sysFiles = CurrentConstants.DEFAULT_SCAN_PACAGE;
        Properties prop = getDisconfProp();
        sysFiles = prop.getProperty(CurrentConstants.APP_SYS_FILES);
        List<String> sysFileList = StringUtil.parseStringToStringList(sysFiles, ",");
        return sysFileList == null ? new ArrayList<>() : sysFileList;
    }
}
