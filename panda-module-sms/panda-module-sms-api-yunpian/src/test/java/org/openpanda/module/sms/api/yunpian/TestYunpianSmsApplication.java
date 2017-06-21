package org.openpanda.module.sms.api.yunpian;

import org.junit.Test;
import org.openpanda.module.sms.api.SmsApplication;

/**
 * Created by lingen on 2017/6/20.
 */
public class TestYunpianSmsApplication {

    @Test
    public void testSendMsg(){
        SmsApplication smsApplication = new YunpianSmsApplication("xxx");
        smsApplication.sendMsg("18620501006","【点滴移动】您的验证码是 231452");
    }

    @Test
    public void testSendMsgs(){
        SmsApplication smsApplication = new YunpianSmsApplication("xxx");
        smsApplication.sendMsgs(new String[]{"18620501006"},"【点滴移动】您的验证码是 231452");
    }
}
