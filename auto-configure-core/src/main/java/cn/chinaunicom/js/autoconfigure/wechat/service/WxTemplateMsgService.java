package cn.chinaunicom.js.autoconfigure.wechat.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.TemplateMessage;
import cn.chinaunicom.js.autoconfigure.util.GsonUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;

/**
 * @author chenkexiao
 * @date 2019/02/22
 */
public class WxTemplateMsgService {

    protected WxMpService wxMpService;

    public WxTemplateMsgService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    /**
     * @param templateMessage
     * @return
     */
    public boolean sendTemplateMessage(TemplateMessage templateMessage) {
        // TODO 完善修改
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder().toUser("o3U2A1mwuFWBqecfFpkbdge2_F5U")
            .templateId("fo7dwz7Wf3_2Bqc5dLtGGg3ndJhMCCMsPGD7p750_Q0").url(" ").build();

        wxMpTemplateMessage.addData(new WxMpTemplateData("first", dateFormat.format(new Date()), "#FF00FF"))
            .addData(new WxMpTemplateData("remark", RandomStringUtils.randomAlphanumeric(100), "#FF00FF"));
        // TODO msgId要做记录
        String msgId = "";
        try {
            msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            throw new RuntimeException("WxTemplateMsgService.sendTemplateMessage throw exception", e);
        }
        return false;
    }

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder().toUser("o3U2A1mwuFWBqecfFpkbdge2_F5U")
            .templateId("fo7dwz7Wf3_2Bqc5dLtGGg3ndJhMCCMsPGD7p750_Q0").url(" ")
            .miniProgram(new MiniProgram("appid", "pagePath", true)).build();

        wxMpTemplateMessage.addData(new WxMpTemplateData("first", dateFormat.format(new Date()), "#FF00FF"))
            .addData(new WxMpTemplateData("keyword1", dateFormat.format(new Date()), "#FF00FF"))
            .addData(new WxMpTemplateData("keyword2", dateFormat.format(new Date()), "#FF00FF"))
            .addData(new WxMpTemplateData("keyword3", dateFormat.format(new Date()), "#FF00FF"))
            .addData(new WxMpTemplateData("remark", RandomStringUtils.randomAlphanumeric(100), "#FF00FF"));
        System.out.println(GsonUtils.gsonString(wxMpTemplateMessage));

        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setComment("测试");
        JsonObject data = GsonUtils.jsonStringToObject(
            "{\"first\": \"尊敬的用户,恭喜您成功领取腾讯视频VIP会员权益!\",\"keyword2\": \"${phoneNo}\",\"keyword3\": \"即日起至2018年12月31日\",\"remark\": \"为了不影响您的剩余权益领取,需保持参与账号为正常在网状态,不欠费且持续关注并绑定“江苏联通”微信公众号。每个微信号、手机号及QQ号只有一个参与机会哦。点击“详情”查看。\"}");
        templateMessage.setData(data);
        templateMessage.setEnabled(true);
        templateMessage.setTemplateId("templateId");
        templateMessage.setUrl("testUrl");

        System.out.println(GsonUtils.gsonString(templateMessage));

        wxMpTemplateMessage = exchageData(templateMessage, wxMpTemplateMessage);

        System.out.println(GsonUtils.gsonString(wxMpTemplateMessage));
    }

    /**
     * @param templateMessage
     * @param wxMpTemplateMessage
     * @return
     */
    private static WxMpTemplateMessage exchageData(TemplateMessage templateMessage,
        WxMpTemplateMessage wxMpTemplateMessage) {
        // 优先跳转url
        if (StringUtils.isNoneEmpty(templateMessage.getUrl())) {
            wxMpTemplateMessage.setUrl(templateMessage.getUrl());
        } else {
            if (null != templateMessage.getMiniProgram()) {
                wxMpTemplateMessage.setMiniProgram(templateMessage.getMiniProgram());
            }
        }
        wxMpTemplateMessage.setTemplateId(templateMessage.getTemplateId());
        List<WxMpTemplateData> data = wxMpTemplateMessage.getData();
        for (WxMpTemplateData wxMpTemplateData : data) {
            JsonObject bizData = templateMessage.getData();
            for (Map.Entry<String, JsonElement> bizdata : bizData.entrySet()) {
                if (bizdata.getKey().equals(wxMpTemplateData.getName())) {
                    wxMpTemplateData.setValue(bizdata.getValue().getAsString());
                }
            }
        }
        wxMpTemplateMessage.setData(data);
        wxMpTemplateMessage.setToUser(templateMessage.getToUser());
        return wxMpTemplateMessage;
    }
}
