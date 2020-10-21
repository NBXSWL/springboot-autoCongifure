package cn.chinaunicom.js.autoconfigure.disconf.entity.pub;

import java.io.Serializable;

import com.google.gson.JsonObject;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;

/**
 * 业务模板消息类：根据原始的微信模板消息格式配置的具体业务模板消息
 * 
 * @author chenkexiao
 * @date 2019/03/13
 */
public class TemplateMessage implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3780480851694135930L;

    /**
     * 模板说明
     */
    private String comment;

    /**
     * 模板消息具体内容
     */
    private JsonObject data;

    /**
     * 模板消息id
     */
    private String templateId;

    /**
     * 是否启用
     */
    private boolean isEnabled;

    /**
     * 目标用户openid
     */
    private String toUser;

    /**
     * 详情url
     */
    private String url;
    /**
     * 小程序跳转信息
     */
    private MiniProgram miniProgram;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public MiniProgram getMiniProgram() {
        return miniProgram;
    }

    public void setMiniProgram(MiniProgram miniProgram) {
        this.miniProgram = miniProgram;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "TemplateMessage [comment=" + comment + ", data=" + data + ", templateId=" + templateId + ", isEnabled="
            + isEnabled + ", toUser=" + toUser + ", url=" + url + ", miniProgram=" + miniProgram + "]";
    }

}
