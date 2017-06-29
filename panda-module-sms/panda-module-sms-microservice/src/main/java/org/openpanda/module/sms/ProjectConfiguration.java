package org.openpanda.module.sms;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.openpanda.module.sms.config.RedisConfig;

/**
 * Created by lingen on 2017/6/28.
 */
public class ProjectConfiguration extends Configuration {

    private RedisConfig redisConfig;

    private String apiKey;

    private String template;

    @JsonProperty("redisConfig")
    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    @JsonProperty("redisConfig")
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
