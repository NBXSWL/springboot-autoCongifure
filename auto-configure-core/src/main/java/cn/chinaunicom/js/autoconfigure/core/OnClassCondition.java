package cn.chinaunicom.js.autoconfigure.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import cn.chinaunicom.js.autoconfigure.util.DisconfUtils;

/**
 * classpath下有某个class时才加载.
 *
 * @author WangPingChun
 * @since 2019-01-10
 */
public class OnClassCondition extends cn.chinaunicom.js.autoconfigure.core.AbstractCondition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final ClassLoader classLoader = context.getClassLoader();
        // 获得特性注解属性值
        List<String> allFeatures = getDisableFeatures(metadata, ConditionalFeature.class);
        // 未启用的特性不加载配置类，没有配置特性的默认加载
        if (allFeatures == null || allFeatures.isEmpty()) {
            return true;
        } else {
            // 不启用的特性，不加载对应配置类
            for (String features : allFeatures) {
                if (DisconfUtils.getDisableFeatures().contains(features)) {
                    return false;
                }
            }
        }
        // 获得注解的属性
        List<String> onClasses = getCandidates(metadata, ConditionalOnClass.class);
        if (!onClasses.isEmpty()) {
            // missing为空表示onClass里面的类在classpath下都存在
            final List<String> missing = filter(onClasses, ClassNameFilter.MISSING, classLoader);
            return missing.isEmpty();
        }

        final List<String> onMissingClasses = getCandidates(metadata, ConditionalOnMissingClass.class);
        if (!onMissingClasses.isEmpty()) {
            // present为空表示onMissingClasses里面的类在classpath下都不存在
            final List<String> present = filter(onClasses, ClassNameFilter.PRESENT, classLoader);
            return present.isEmpty();
        }

        return true;
    }

    /**
     * @param metadata
     * @param class1
     * @return
     */
    private List<String> getDisableFeatures(AnnotatedTypeMetadata metadata, Class<?> annotationType) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(annotationType.getName(), true);
        List<String> candidates = new ArrayList<>();
        if (attributes != null) {
            addAll(candidates, attributes.get("featureName"));
        }
        return candidates;
    }

    /**
     * 获取注解内标记的Class的全限定路径.
     *
     * @param metadata
     *            注解的元数据
     * @param annotationType
     *            注解的Class
     * @return 注解内标记的Class的全限定路径
     */
    private List<String> getCandidates(AnnotatedTypeMetadata metadata, Class<?> annotationType) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(annotationType.getName(), true);

        List<String> candidates = new ArrayList<>();

        if (attributes != null) {
            addAll(candidates, attributes.get("value"));
            addAll(candidates, attributes.get("name"));
        }

        return candidates;
    }

    /**
     * 把itemsToAdd里面所有元素由Object强制转成String[]并添加到list中.
     *
     * @param list
     *            被添加的集合
     * @param itemsToAdd
     *            待添加的集合
     */
    private void addAll(List<String> list, List<Object> itemsToAdd) {
        if (itemsToAdd != null) {
            for (Object item : itemsToAdd) {
                Collections.addAll(list, (String[])item);
            }
        }
    }

    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }
}
