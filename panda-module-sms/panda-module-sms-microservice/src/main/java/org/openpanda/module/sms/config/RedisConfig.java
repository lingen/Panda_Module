package org.openpanda.module.sms.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lingen on 2017/6/28.
 */
public class RedisConfig {

    private String ip;

    private int port;

    private String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
