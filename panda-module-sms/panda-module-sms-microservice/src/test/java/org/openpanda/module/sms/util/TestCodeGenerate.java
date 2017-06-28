package org.openpanda.module.sms.util;

import org.junit.Assert;
import org.junit.Test;
import org.openpanda.module.sms.code.CodeGenerate;
import org.openpanda.module.sms.code.impl.RedisCodeGenerate;

/**
 * Created by lingen on 2017/6/28.
 */
public class TestCodeGenerate {

    @Test
    public void testCodeGenerate(){
        CodeGenerate codeGenerate = new RedisCodeGenerate("127.0.0.1",123,"aaa");
        String validCode = codeGenerate.generateCode("18620501006");

        boolean checkFirst = codeGenerate.validCode("18620501006",validCode);

        boolean checkSecond = codeGenerate.validCode("18620501006","1234");

        Assert.assertTrue(checkFirst);

        Assert.assertTrue(!checkSecond);
    }
}
