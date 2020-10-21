package cn.chinaunicom.js.autoconfigure.beans.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import cn.chinaunicom.js.autoconfigure.core.ConditionalFeature;
import cn.chinaunicom.js.autoconfigure.core.ConditionalOnClass;
import cn.chinaunicom.js.autoconfigure.disconf.entity.current.KafkaConfig;
import cn.chinaunicom.js.autoconfigure.util.ConfigBeanUtils;

/**
 * kafka配置类，包括消费者和生产者配置
 *
 * @author WangPingChun
 * @since 2019-01-10
 */
@EnableKafka
@Configuration
@ConditionalFeature(featureName = {"Kafka"})
@ConditionalOnClass({KafkaListenerContainerFactory.class, ConsumerFactory.class, ConsumerConfig.class})
public class KafkaBeanConfig {

    /**
     * kafka消费者配置 begin
     * 
     * @return
     */
    @Bean
    @ConditionalOnClass({KafkaListenerContainerFactory.class})
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>>
        kafkaListenerContainerFactory() {
        KafkaConfig kafkaConfig = ConfigBeanUtils.getBean(KafkaConfig.class);
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(kafkaConfig.getConcurrency());
        factory.getContainerProperties().setPollTimeout(kafkaConfig.getPollTimeout());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.valueOf(kafkaConfig.getAckMode()));
        return factory;
    }

    @Bean
    @ConditionalOnClass({ConsumerFactory.class})
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * kafka消费者配置 end
     * 
     * @return
     */
    @Bean
    @ConditionalOnClass({ConsumerConfig.class})
    public Map<String, Object> consumerConfigs() {
        KafkaConfig kafkaConfig = ConfigBeanUtils.getBean(KafkaConfig.class);
        if (StringUtils.isBlank(kafkaConfig.getGroupId())) {
            throw new RuntimeException("Need to config group.id in file KafkaConfig.properties");
        }
        Map<String, Object> props = new HashMap<>(16);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        try {
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Class.forName(kafkaConfig.getKeyDeserializer()));
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                Class.forName(kafkaConfig.getValueDeserializer()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Init consumerConfigs failed:method producerConfigs() throws a ClassNotFoundException", e);
        }
        // 禁止自动提交(自动提交会每隔一段时间提交一次偏移量,在处理较复杂的业务时可能会导致数据丢失)
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConfig.isAutoCommit());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getGroupId());
        return props;
    }

    /**
     * 生产者配置
     * 
     * @return
     */
    @Bean
    @ConditionalOnClass({ProducerConfig.class})
    public Map<String, Object> producerConfigs() {
        KafkaConfig kafkaConfig = ConfigBeanUtils.getBean(KafkaConfig.class);
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaConfig.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaConfig.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaConfig.getLinger());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaConfig.getBufferMemory());
        try {
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Class.forName(kafkaConfig.getKeySerializer()));
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Class.forName(kafkaConfig.getValueSerializer()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Init producerConfigs failed:method producerConfigs() throws a ClassNotFoundException", e);

        }
        return props;
    }

    /**
     * 获取工厂
     */
    @Bean
    @ConditionalOnClass({ProducerFactory.class})
    public ProducerFactory<String, String> producerFactory() {
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerConfigs());
        return producerFactory;
    }

    /**
     * 注册实例
     */
    @Bean
    @ConditionalOnClass({KafkaTemplate.class})
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
