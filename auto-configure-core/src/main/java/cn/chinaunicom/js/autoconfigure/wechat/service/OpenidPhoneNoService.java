package cn.chinaunicom.js.autoconfigure.wechat.service;

/**
 * 根据绑定的openid或者手机号，由具体实现类处理
 * 
 * @author chenkexiao
 * @date 2019/03/22
 */
public abstract class OpenidPhoneNoService {
    /**
     * 根据手机号查询openid
     * 
     * @param phoneNo
     * @return
     */
    public abstract String getOpenidByPhoneNo(String phoneNo);

    /**
     * 根据openid查询绑定手机号
     * 
     * @param Openid
     * @return
     */
    public abstract String getPhoneNoByOpenid(String openid);
}
