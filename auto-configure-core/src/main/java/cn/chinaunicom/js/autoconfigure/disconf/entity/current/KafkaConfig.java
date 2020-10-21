package cn.chinaunicom.js.autoconfigure.disconf.entity.current;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

import cn.chinaunicom.js.autoconfigure.disconf.entity.base.ConfigFile;

/**
 * kafka配置信息，包括消费者和生产者配置
 * 
 * @author chenkexiao
 * @date 2019/03/20
 */
@Service
@DisconfFile(filename = "KafkaConfig.properties")
public class KafkaConfig implements ConfigFile {
    /**
     * kafka配置信息，包括消费者和生产者配置
     */
    private String bootstrapServers;

    private String groupId;

    private boolean autoCommit;

    private int sessionTimout;

    private int autoCommitInterval;

    private String autoOffsetReset;

    private int concurrency;

    private int retries;

    private int pollTimeout;
    private String keyDeserializer;
    private String valueDeserializer;
    private String ackMode;
    /**
     * 以下为生产者配置
     */
    private int batchSize;
    private int linger;
    private int bufferMemory;
    private String keySerializer;
    private String valueSerializer;

    @DisconfFileItem(name = "kafka.bootstrap.servers")
    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @DisconfFileItem(name = "kafka.consumer.group.id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    @DisconfFileItem(name = "kafka.consumer.session.timeout")
    public int getSessionTimout() {
        return sessionTimout;
    }

    public void setSessionTimout(int sessionTimout) {
        this.sessionTimout = sessionTimout;
    }

    @DisconfFileItem(name = "kafka.consumer.auto.commit.interval")
    public int getAutoCommitInterval() {
        return autoCommitInterval;
    }

    public void setAutoCommitInterval(int autoCommitInterval) {
        this.autoCommitInterval = autoCommitInterval;
    }

    @DisconfFileItem(name = "kafka.consumer.auto.offset.reset")
    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    @DisconfFileItem(name = "kafka.consumer.concurrency")
    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    @DisconfFileItem(name = "kafka.producer.retries")
    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    @DisconfFileItem(name = "kafka.producer.batch.size")
    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @DisconfFileItem(name = "kafka.producer.linger")
    public int getLinger() {
        return linger;
    }

    public void setLinger(int linger) {
        this.linger = linger;
    }

    @DisconfFileItem(name = "kafka.producer.buffer.memory")
    public int getBufferMemory() {
        return bufferMemory;
    }

    public void setBufferMemory(int bufferMemory) {
        this.bufferMemory = bufferMemory;
    }

    @DisconfFileItem(name = "kafka.consumer.poll.timeout")
    public int getPollTimeout() {
        return pollTimeout;
    }

    public void setPollTimeout(int pollTimeout) {
        this.pollTimeout = pollTimeout;
    }

    @DisconfFileItem(name = "kafka.consumer.key.deserializer")
    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public void setKeyDeserializer(String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    @DisconfFileItem(name = "kafka.consumer.value.deserializer")
    public String getValueDeserializer() {
        return valueDeserializer;
    }

    public void setValueDeserializer(String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    @DisconfFileItem(name = "kafka.consumer.ackmode")
    public String getAckMode() {
        return ackMode;
    }

    public void setAckMode(String ackMode) {
        this.ackMode = ackMode;
    }

    @DisconfFileItem(name = "kafka.producer.key.serializer")
    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    @DisconfFileItem(name = "kafka.producer.value.serializer")
    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    @Override
    public String toString() {
        return "KafkaConfig [bootstrapServers=" + bootstrapServers + ", groupId=" + groupId + ", autoCommit="
            + autoCommit + ", sessionTimout=" + sessionTimout + ", autoCommitInterval=" + autoCommitInterval
            + ", autoOffsetReset=" + autoOffsetReset + ", concurrency=" + concurrency + ", retries=" + retries
            + ", pollTimeout=" + pollTimeout + ", keyDeserializer=" + keyDeserializer + ", valueDeserializer="
            + valueDeserializer + ", ackMode=" + ackMode + ", batchSize=" + batchSize + ", linger=" + linger
            + ", bufferMemory=" + bufferMemory + ", keySerializer=" + keySerializer + ", valueSerializer="
            + valueSerializer + "]";
    }

}
