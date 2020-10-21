package cn.chinaunicom.js.autoconfigure.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

/**
 * @author chenkexiao
 * @date 2019/04/01
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalFeature {
    /**
     * 只有配置了指定的特性名称，才可以加载对应配置类.
     *
     * @return 特性名称
     */
    String[] featureName() default {};
}
