package org.openpanda.module.sms.api.yunpian;

import org.openpanda.module.sms.api.SmsApplication;
import org.openpanda.pandalib.http.HttpRequest;
import org.openpanda.pandalib.http.HttpRequestMethod;
import org.openpanda.pandalib.http.HttpResponse;
import org.openpanda.pandalib.http.NetworkSession;

import java.util.HashMap;

/**
 * Created by lingen on 2017/6/17.
 */
public class YunpianSmsApplication implements SmsApplication {

    private static String YUNPIAN_SINGLE_SEND_URL = "https://sms.yunpian.com/v2/sms/single_send.json";

    private static String YUNPIAN_BATCH_SEND_URL = "https://sms.yunpian.com/v2/sms/batch_send.json";

    private static String YunPian_Api_Key = "apikey";

    private static String YunPian_Mobile = "mobile";

    private static String YunPian_Text = "text";

    private String apiKey;


    public YunpianSmsApplication(String apiKey){
        this.apiKey = apiKey;
    }


    @Override
    public void sendMsg(String mobile, String template) {
        HttpRequest request = HttpRequest.formRequest(YUNPIAN_SINGLE_SEND_URL, HttpRequestMethod.HTTP_POST);
        request.setParams(new HashMap<String,Object>(){
            {
                put(YunPian_Api_Key,apiKey);
                put(YunPian_Mobile,mobile);
                put(YunPian_Text,template);
            }
        });

        HttpResponse response = NetworkSession.sharedInstance().syncRequest(request);
        if (!response.isRequestOK()){
            throw new RuntimeException(response.getErrorMsg());
        }
    }

    @Override
    public void sendMsgs(String mobiles[], String template) {
        StringBuffer mobileString = new StringBuffer();
        for(String mobile : mobiles){
            mobileString.append(mobile+",");
        }

        HttpRequest request = HttpRequest.formRequest(YUNPIAN_BATCH_SEND_URL, HttpRequestMethod.HTTP_POST);
        request.setParams(new HashMap<String,Object>(){
            {
                put(YunPian_Api_Key,apiKey);
                put(YunPian_Mobile,mobileString.toString());
                put(YunPian_Text,template);
            }
        });

        HttpResponse response = NetworkSession.sharedInstance().syncRequest(request);
        if (!response.isRequestOK()){
            throw new RuntimeException(response.getErrorMsg());
        }
    }
}
