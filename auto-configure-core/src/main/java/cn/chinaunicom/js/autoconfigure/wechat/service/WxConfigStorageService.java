package cn.chinaunicom.js.autoconfigure.wechat.service;

import com.google.gson.JsonObject;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.WxMpSettingsConfig;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;
import cn.chinaunicom.js.autoconfigure.util.GsonUtils;
import cn.chinaunicom.js.autoconfigure.wechat.entity.WxConfigStorage;
import redis.clients.jedis.JedisCluster;

/**
 * @author chenkexiao
 * @date 2019/02/22
 */
public class WxConfigStorageService {
    private final String WECHAT_ACCESSTOKEN_KEY = "wechat_accesstoken_key";
    private JedisCluster jedisCluster;

    public WxConfigStorageService() {}

    public WxConfigStorageService(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    /**
     * @param templateMessage
     * @return
     */
    public WxConfigStorage getWxConfigStorage() {
        WxConfigStorage configStorage = new WxConfigStorage();
        WxMpSettingsConfig wxMpSettings = ConfigBeanUtils.getBean(WxMpSettingsConfig.class);
        JsonObject accessToken = this.getAccessToken();
        configStorage.setAccessToken(
            accessToken.get("access_token") == null ? "" : accessToken.get("access_token").getAsString());
        configStorage.setAppId(wxMpSettings.getAppId());
        configStorage.setToken(wxMpSettings.getToken());
        configStorage.setAesKey(wxMpSettings.getAesKey());
        configStorage.setSecret(wxMpSettings.getAesKey());
        configStorage
            .setExpiresTime(accessToken.get("expiresTill") == null ? 0L : accessToken.get("expiresTill").getAsLong());
        return configStorage;
    }

    /**
     * 现有redis缓存accesstoken格式：{"expiresTill":1552636750033,"access_token":"","expires_in":7200}
     * 
     * @return
     */
    private JsonObject getAccessToken() {
        JsonObject accessToken = new JsonObject();
        // 通过redis获取accessToken，兼容现有的微信SDK
        String accessTokenJsonStr = "";
        if (jedisCluster.exists(WECHAT_ACCESSTOKEN_KEY)) {
            accessTokenJsonStr = jedisCluster.get(WECHAT_ACCESSTOKEN_KEY);
        }
        accessToken = GsonUtils.jsonStringToObject(accessTokenJsonStr);
        return accessToken;
    }
}
