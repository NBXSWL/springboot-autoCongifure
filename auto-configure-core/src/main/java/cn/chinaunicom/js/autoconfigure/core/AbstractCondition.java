package cn.chinaunicom.js.autoconfigure.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author WangPingChun
 * @since 2019-01-10
 */
abstract class AbstractCondition implements ConfigurationCondition {
    protected List<String> filter(Collection<String> classNames, ClassNameFilter classNameFilter,
        ClassLoader classLoader) {
        if (CollectionUtils.isEmpty(classNames)) {
            return Collections.emptyList();
        }
        List<String> matches = new ArrayList<>(classNames.size());
        for (String candidate : classNames) {
            if (classNameFilter.matches(candidate, classLoader)) {
                matches.add(candidate);
            }
        }
        return matches;
    }

    protected enum ClassNameFilter {
        /**
         * 必须要存在某个class.
         */
        PRESENT {
            @Override
            public boolean matches(String className, ClassLoader classLoader) {
                return isPresent(className, classLoader);
            }

        },

        /**
         * 必须不要存在某个class.
         */
        MISSING {
            @Override
            public boolean matches(String className, ClassLoader classLoader) {
                return !isPresent(className, classLoader);
            }

        };

        /**
         * 判断classpath下是否有指定的className.
         *
         * @param className
         *            className
         * @param classLoader
         *            classLoader
         * @return 是否存在className的class
         */
        public abstract boolean matches(String className, ClassLoader classLoader);

        /**
         * 判断classpath下是否有指定的className.
         *
         * @param className
         *            className
         * @param classLoader
         *            classLoader
         * @return 是否存在className的class
         */
        public static boolean isPresent(String className, ClassLoader classLoader) {
            if (classLoader == null) {
                classLoader = ClassUtils.getDefaultClassLoader();
            }
            try {
                forName(className, classLoader);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        /**
         * 加载className.
         *
         * @param className
         *            className
         * @param classLoader
         *            classLoader
         * @return Class
         * @throws ClassNotFoundException
         *             class not exist
         */
        private static Class<?> forName(String className, ClassLoader classLoader) throws ClassNotFoundException {
            if (classLoader != null) {
                // 类加载器加载,不会初始化类
                return classLoader.loadClass(className);
            }
            // 使用反射查询class,会初始化类
            return Class.forName(className);
        }

    }

}
