package org.openpanda.module.sms.code.impl;

import org.openpanda.module.sms.code.CodeGenerate;

/**
 * Created by lingen on 2017/6/21.
 * CodeGenerate的EHCache的实现，这是一个单机
 */
public class RedisCodeGenerate implements CodeGenerate {

    @Override
    public String generateCode(String mobile) {
        return null;
    }

    @Override
    public boolean validCode(String mobile, String code) {
        return false;
    }
}
