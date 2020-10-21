package cn.chinaunicom.js.autoconfigure.disconf.entity.base;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * @author chenkexiao
 * @date 2019/03/12
 */
@Service
@DisconfFile(filename = "WxMpSettings.properties", app = "base")
public class WxMpSettingsConfig implements ConfigFile {
    private String appId;
    private String secret;
    private String token;
    private String aesKey;

    @DisconfFileItem(name = "base.appId")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @DisconfFileItem(name = "base.secret")
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @DisconfFileItem(name = "base.token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @DisconfFileItem(name = "base.aesKey")
    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public String toString() {
        return "WxMpSettings [appId=" + appId + ", secret=" + secret + ", token=" + token + ", aesKey=" + aesKey + "]";
    }

}
