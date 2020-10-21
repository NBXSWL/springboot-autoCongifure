package cn.chinaunicom.js.autoconfigure.beans.disconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;

import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;

/**
 * disconf自动配置类.
 *
 * @author chenkexiao
 * @since 2019-01-10
 */
@Component
@ConditionalOnClass({DisconfMgrBean.class, DisconfMgrBeanSecond.class})
public class DisconfBeanConfig {

    @SuppressWarnings("unused")
    @Autowired
    private DisconfMgrBean disconfMgrBean;
    @SuppressWarnings("unused")
    @Autowired
    private DisconfMgrBeanSecond disconfMgrBeanSecond;

    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean disconfMgrBean() {
        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        disconfMgrBean.setScanPackage(DisconfUtils.getScanPackage());
        return disconfMgrBean;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    @Lazy(false)
    public DisconfMgrBeanSecond disconfMgrBeanSecond() {
        DisconfMgrBeanSecond disconfMgrBeanSecond = new DisconfMgrBeanSecond();
        return disconfMgrBeanSecond;
    }
}
