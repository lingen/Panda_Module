package org.openpanda.module.sms.api;

/**
 * Created by lingen on 2017/6/17.
 */
public interface SmsApplication {

    /**
     * Send a code message to mobile with fixed template
     * @param mobile
     * @param template
     */
    void sendMsg(String mobile, String template);

    /**
     * send message to multi mobiles
     * @param mobiles
     * @param template
     */
    void sendMsgs(String[] mobiles,String template);

}
