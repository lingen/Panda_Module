package org.openpanda.module.sms.resources;

import com.codahale.metrics.annotation.Timed;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.openpanda.microservice.ddd.rule.PandaResourceExecuter;
import org.openpanda.module.sms.api.SmsApplication;
import org.openpanda.module.sms.api.yunpian.YunpianSmsApplication;
import org.openpanda.module.sms.code.CodeGenerate;
import org.openpanda.module.sms.code.impl.RedisCodeGenerate;
import org.openpanda.module.sms.config.RedisConfig;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by lingen on 2017/6/29.
 */
@Path("/sms")
@Produces(MediaType.APPLICATION_JSON)
public class SmsResources {

    private static SmsApplication smsApplication;

    private static CodeGenerate codeGenerate;

    private static String template;

    private static RedisConfig redisConfig;

    private static String apiKey;

    public SmsResources(RedisConfig redisConfig,String apiKey,String template){
        SmsResources.redisConfig = redisConfig;
        SmsResources.template = template;
        SmsResources.apiKey = apiKey;
    }

    @POST
    @Timed
    public Response sendSms(final Map params){

        return PandaResourceExecuter.executeWithReturnBool(()->{
            List<String> mobiles = (List<String>) params.get("mobiles");
            String template = (String) params.get("template");
            if (StringUtils.isEmpty(template)){
                template = this.template;
            }
            if (mobiles==null || mobiles.size() == 0){
                return false;
            }

            for(String mobile:mobiles){
                String random = getCodeGenerate().generateCode(mobile);
                getSmsApplication().sendMsg(mobile,String.format(template,random));
            }
            return true;
        });
    }

    @PUT
    @Timed
    public Response checkSms(final Map params){
        return PandaResourceExecuter.executeWithReturnBool(()->{
            String mobile = (String) params.get("mobile");
            String validCode = (String) params.get("validCode");
            return getCodeGenerate().validCode(mobile,validCode);
        });
    }


    public static SmsApplication getSmsApplication(){
        if (smsApplication == null){
            smsApplication = new YunpianSmsApplication(SmsResources.apiKey);
        }
        return smsApplication;
    }

    public static CodeGenerate getCodeGenerate(){
        if (codeGenerate == null){
            codeGenerate = new RedisCodeGenerate(SmsResources.redisConfig.getIp(),SmsResources.redisConfig.getPort(),SmsResources.redisConfig.getPassword());
        }
        return codeGenerate;
    }
}
