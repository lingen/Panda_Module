package org.openpanda.module.sms;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.openpanda.module.sms.config.RedisConfig;

/**
 * Created by lingen on 2017/6/28.
 */
public class ProjectConfiguration extends Configuration {

    private RedisConfig redisConfig;

    @JsonProperty("redisConfig")
    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    @JsonProperty("redisConfig")
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }
}
