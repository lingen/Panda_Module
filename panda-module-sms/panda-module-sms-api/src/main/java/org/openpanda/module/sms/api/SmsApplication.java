package org.openpanda.module.sms.api;

/**
 * Created by lingen on 2017/6/17.
 */
public interface SmsApplication {

    /**
     * Send Message to mobile with fixed content
     * @param mobile
     * @param content
     */
    void sendSms(String mobile,String content);

    /**
     * Send a code message to mobile with fixed template
     * @param mobile
     * @param template
     */
    void sendValidCode(String mobile,String template);


    /**
     * Valid Code
     * @param mobile
     * @param code
     */
    void checkValidCode(String mobile,String code);

}
