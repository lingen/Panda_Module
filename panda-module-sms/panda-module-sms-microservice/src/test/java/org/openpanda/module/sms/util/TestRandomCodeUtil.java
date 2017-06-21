package org.openpanda.module.sms.util;

import org.junit.Test;

/**
 * Created by lingen on 2017/6/21.
 */
public class TestRandomCodeUtil {

    @Test
    public void testRandomSix(){
        for (int i=0;i<100;i++){
            System.out.println(RandomCodeUtil.randomSix());
        }
    }
}
