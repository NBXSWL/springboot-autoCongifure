package cn.chinaunicom.js.autoconfigure.wechat.service;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.TemplateMessageConfig;
import cn.chinaunicom.js.autoconfigure.disconf.entity.pub.TemplateMessage;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;

/**
 * 发送模板消息
 * 
 * @author chenkexiao
 *
 */
public class TemplateMessageService {
    private static Logger logger = LoggerFactory.getLogger(TemplateMessageService.class);
    private WxTemplateMsgService wxTemplateMsgService;

    public TemplateMessageService(WxTemplateMsgService wxTemplateMsgService) {
        this.wxTemplateMsgService = wxTemplateMsgService;
    }

    /**
     * 根据openid发送模板消息
     * 
     * @param templateMessageId
     *            配置的模板消息标志
     * @param openid
     *            公众号openid
     * @return
     */
    public boolean sendTemplateMessageByOpenid(String templateMessageId, String openid) {
        boolean result = false;
        TemplateMessageConfig templateMessageConfig = ConfigBeanUtils.getBean(TemplateMessageConfig.class);
        TemplateMessage templateMessage = templateMessageConfig.get(templateMessageId);
        logger.info("TemplateMessageService.sendTemplateMessageByOpenid templateMessage:" + templateMessage);
        if (templateMessage != null && templateMessage.isEnabled()) {
            templateMessage.setToUser(openid);
            result = wxTemplateMsgService.sendTemplateMessage(templateMessage);
        }
        return result;
    }

    /**
     * 根据openid发送模板消息
     * 
     * @param templateMessageId
     *            配置的模板消息标志
     * @param phoneNo
     *            公众号绑定手机号
     * @return
     */
    public boolean sendTemplateMessageByPhoneNo(String templateMessageId, String phoneNo,
        OpenidPhoneNoService openidPhoneNoService) {
        boolean result = false;
        TemplateMessageConfig templateMessageConfig = ConfigBeanUtils.getBean(TemplateMessageConfig.class);
        TemplateMessage templateMessage = templateMessageConfig.get(templateMessageId);
        logger.info("TemplateMessageService.sendTemplateMessageByPhoneNo templateMessage:" + templateMessage);
        if (templateMessage != null && templateMessage.isEnabled()) {
            // 根据手机号查询openid
            String openid = openidPhoneNoService.getOpenidByPhoneNo(phoneNo);
            if (StringUtils.isEmpty(openid)) {
                return false;
            }
            templateMessage.setToUser(openid);
            result = wxTemplateMsgService.sendTemplateMessage(templateMessage);
        }
        return result;
    }

    /**
     * 发送模板消息,openid和手机号都需要时
     * 
     * @param templateMessageId
     *            配置的模板消息标志
     * @param phoneNo
     *            公众号绑定手机号
     * @return
     */
    public boolean sendTemplateMessage(String templateMessageId, String openid, String phoneNo,
        OpenidPhoneNoService openidPhoneNoService) {
        if (StringUtils.isEmpty(openid) && StringUtils.isEmpty(phoneNo)) {
            return false;
        }
        // 根据手机号查询openid
        if (StringUtils.isEmpty(openid) && StringUtils.isNotEmpty(phoneNo)) {
            openid = openidPhoneNoService.getOpenidByPhoneNo(phoneNo);
        }
        if (StringUtils.isEmpty(openid)) {
            return false;
        }
        TemplateMessageConfig templateMessageConfig = ConfigBeanUtils.getBean(TemplateMessageConfig.class);
        TemplateMessage templateMessage = templateMessageConfig.get(templateMessageId);
        logger.info("TemplateMessageService.sendTemplateMessage templateMessage1:" + templateMessage);
        if (templateMessage != null && templateMessage.isEnabled()) {
            templateMessage.setToUser(openid);
            return wxTemplateMsgService.sendTemplateMessage(templateMessage);
        }
        return false;
    }

    /**
     * 发送模板消息,openid和手机号都需要时
     * 
     * @param templateMessageId
     *            配置的模板消息标志
     * @param phoneNo
     *            公众号绑定手机号
     * @return
     */
    public boolean sendTemplateMessage(TemplateMessage templateMessage) {
        logger.info("TemplateMessageService.sendTemplateMessage templateMessage2:" + templateMessage);
        if (templateMessage != null && templateMessage.isEnabled()) {
            if (StringUtils.isEmpty(templateMessage.getToUser())) {
                return false;
            }
            return wxTemplateMsgService.sendTemplateMessage(templateMessage);
        }
        return false;
    }

    private String dealVariable(String origin, String phoneNo) {
        String target = origin;
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        if (target.contains("${currentDate}")) {
            target = target.replace("${currentDate}", new DateTime().toString(fmt));
        }
        if (target.contains("${phoneNo}")) {
            // 格式化手机号码中间替换为"****"
            phoneNo = phoneNo.substring(0, phoneNo.length() - 8) + "****" + phoneNo.substring(phoneNo.length() - 4);
            target = target.replace("${phoneNo}", phoneNo);
        }
        return target;
    }
}
