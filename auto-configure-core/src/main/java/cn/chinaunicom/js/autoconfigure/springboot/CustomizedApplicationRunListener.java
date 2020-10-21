package cn.chinaunicom.js.autoconfigure.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;

import com.ejoined.commons.plugin.utils.SpringContextHolder;

import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;
import cn.chinaunicom.js.autoconfigure.util.DubboIpsUtils;

public class CustomizedApplicationRunListener implements SpringApplicationRunListener, PriorityOrdered {
    public static Logger logger = LoggerFactory.getLogger(CustomizedApplicationRunListener.class);

    public CustomizedApplicationRunListener(SpringApplication application, String[] args) {}

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void starting() {
        String filePath = DisconfUtils.downloadDubboIpFile();
        DubboIpsUtils.setDubboSystemProperties(filePath);
        DisconfUtils.downloadYmlFile();
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {}

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        new SpringContextHolder().setApplicationContext(context);

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {}

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
