package cn.chinaunicom.js.autoconfigure.beans.spring;

import java.io.IOException;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 自定义过滤器
 * 
 * @author chenkexiao
 * @date 2019/03/27
 */
public class CustomTypeFilter implements TypeFilter {
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
        throws IOException {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // Resource resource = metadataReader.getResource();
        // String className = classMetadata.getClassName();
        String annotationName = annotationMetadata.getClassName();
        // 检测注解名字包含Controller的bean
        if (annotationName.contains("Controller")) {
            return true;
        }
        return false;
    }
}