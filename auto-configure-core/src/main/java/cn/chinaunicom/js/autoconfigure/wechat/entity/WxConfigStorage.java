package cn.chinaunicom.js.autoconfigure.wechat.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

/**
 * @author chenkexiao
 * @date 2019/03/12
 */
@XStreamAlias("xml")
public class WxConfigStorage extends WxMpInMemoryConfigStorage {
    /**
     *
     */
    private static final long serialVersionUID = -6837225907294759002L;

    private String appId;
    private String secret;
    private String token;
    private String aesKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public String toString() {
        return "WxConfigStorage [appId=" + appId + ", secret=" + secret + ", token=" + token + ", aesKey=" + aesKey
            + "]";
    }

}
