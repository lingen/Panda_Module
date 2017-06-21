package org.openpanda.module.sms.code;

/**
 * Created by lingen on 2017/6/21.
 * 验证码生成器接口
 */
public interface CodeGenerate {

    /**
     * 为一个手机号生成验证码
     * @param mobile
     * @return
     */
    String generateCode(String mobile);

    /**
     * 传入一个手机号及检验码，检查是否匹配
     * @param mobile
     * @param code
     * @return
     */
    boolean validCode(String mobile,String code);
}
