package info.yymh.blogmanager.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.utils
 * @ClassName:
 * @date 2020/3/23
 * @Description 读取配置文件中的值
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@PropertySource(value = "config.properties")
public class ConfigBean {
    private String key;
    private long ttlMillis;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtlMillis() {
        return ttlMillis;
    }

    public void setTtlMillis(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }
}
