package org.openpanda.module.sms.code.impl;

import org.dayatang.cache.Cache;
import org.dayatang.cache.redis.RedisBasedCache;
import org.openpanda.module.sms.code.CodeGenerate;
import org.openpanda.module.sms.util.RandomCodeUtil;

/**
 * Created by lingen on 2017/6/21.
 * CodeGenerate的EHCache的实现，这是一个单机
 */
public class RedisCodeGenerate implements CodeGenerate {

    private static String PREFIX = "CODE_GENERATE_";

    private String ip;

    private int port;

    private String password;

    private Cache cache;

    public RedisCodeGenerate(String ip,int port,String password){
        this.ip = ip;
        this.port = port;
        this.password = password;
        this.cache = new RedisBasedCache(ip,port,password);
    }

    @Override
    public String generateCode(String mobile) {
        String validCode = RandomCodeUtil.randomSix();
        cache.put(PREFIX+mobile,validCode,  60 * 2);
        return validCode;
    }

    @Override
    public boolean validCode(String mobile, String code) {

        String validCode = (String) cache.get(PREFIX+mobile);
        if (validCode.equals(code)){
            return true;
        }
        return false;
    }
}
