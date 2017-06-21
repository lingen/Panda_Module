package org.openpanda.module.sms.util;

/**
 * Created by lingen on 2017/6/21.
 * 验证码生成器
 */
public class RandomCodeUtil {

    private static String randomNum(int number){
        StringBuffer result = new StringBuffer();

        for (int i=0;i<number;i++){
            int random = (int)(Math.random()*10);
            result.append(random);
        }

        return result.toString();
    }


    public static String randomSix(){
        return RandomCodeUtil.randomNum(6);
    }

    public static String randomFour(){
        return RandomCodeUtil.randomNum(4);
    }

}
