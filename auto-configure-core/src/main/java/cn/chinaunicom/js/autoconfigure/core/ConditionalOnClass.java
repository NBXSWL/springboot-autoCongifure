package cn.chinaunicom.js.autoconfigure.core;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author WangPingChun
 * @since 2019-01-10
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {

    /**
     * 只有classpath下出现指定的类名才可以加载配置类.
     *
     * @return classpath下要出现的类名
     */
    Class<?>[] value() default {};

    /**
     * 只有classpath下出现指定的类名才可以加载配置类.
     *
     * @return classpath下要出现的类名
     */
    String[] name() default {};

}
