package org.openpanda.module.account.application;

import io.dropwizard.testing.DropwizardTestSupport;
import org.dayatang.domain.InstanceFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openpanda.microservice.ddd.domain.PandaPage;
import org.openpanda.microservice.test.PandaDomainTest;
import org.openpanda.module.account.Application;
import org.openpanda.module.account.Configuration;
import org.openpanda.module.account.dto.AccountDTO;

import java.util.ArrayList;
import java.util.List;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

/**
 * Created by lingen on 2016/12/23.
 */
public class TestAccountApplication {

    public static final DropwizardTestSupport<Configuration> ACCOUNT_SUPPORT =
            new DropwizardTestSupport<>(Application.class, resourceFilePath("test-config.yaml"));

    private static AccountApplication accountService;

    private static AccountDTO accountDTO;

    @BeforeClass
    public static void setUp() {
        ACCOUNT_SUPPORT.before();
        accountService = InstanceFactory.getInstance(AccountApplication.class);

        prepareData();
    }

    @AfterClass
    public static void tearDown() {
        ACCOUNT_SUPPORT.after();
    }

    private static void prepareData(){
        accountDTO = new AccountDTO();
        accountDTO.setEmail("lingen@foxmail.com");
        accountDTO.setUsername("lingen");
        accountDTO.setMobile("18620501006");
        accountDTO.setPassword("lingen123");

        accountDTO = accountService.createAccount(accountDTO);
        Assert.assertNotNull(accountDTO.getUid());
    }

    @Test
    public void testCreateAccount(){
        PandaDomainTest.testInDB(()->{
            AccountDTO accountDTO2 = accountService.queryAccountByUid(accountDTO.getUid());
            Assert.assertTrue(accountDTO.getUsername().equals(accountDTO2.getUsername()));
        });

    }

    @Test
    public void testQueryAccountByUid(){
        PandaDomainTest.testInDB(()->{
            AccountDTO accountDTO2 = accountService.queryAccountByUid(accountDTO.getUid());
            Assert.assertTrue(accountDTO.getUsername().equals(accountDTO2.getUsername()));
        });

    }


    @Test
    public void testQueryAccountByUsername(){
        PandaDomainTest.testInDB(()->{
            AccountDTO accountDTO2 = accountService.queryAccountByUsername("lingen");
            Assert.assertTrue(accountDTO.getUid().equals(accountDTO2.getUid()));
        });

    }

    @Test
    public void testQueryAccountByEmail(){
        PandaDomainTest.testInDB(()->{
            AccountDTO accountDTO2 = accountService.queryAccountByEmail("lingen@foxmail.com");
            Assert.assertTrue(accountDTO.getUid().equals(accountDTO2.getUid()));
        });

    }

    @Test
    public void testQueryAccountByMobile(){
        PandaDomainTest.testInDB(()->{
            AccountDTO accountDTO2 = accountService.queryAccountByMobile("18620501006");
            Assert.assertTrue(accountDTO.getUid().equals(accountDTO2.getUid()));
        });

    }


    @Test
    public void testChangePwd(){
        PandaDomainTest.testInDB(()->{
            boolean success = accountService.changePwd(accountDTO.getUid(),"lingen123","lingen");
            Assert.assertTrue(success);

            success = accountService.changePwd(accountDTO.getUid(),"lingen","lingen123");
            Assert.assertTrue(success);
        });

    }


    @Test
    public void testValidPwd(){
        PandaDomainTest.testInDB(()->{

            boolean success = accountService.validPwd("18620501006","lingen123");
            Assert.assertTrue(success);

            success = accountService.validPwd("18620501006","lingen121");
            Assert.assertFalse(success);
        });


    }

    @Test
    public void testEnabled(){
        PandaDomainTest.testInDB(()->{

            accountService.enabled(new ArrayList(){
                {
                    add(accountDTO.getUid());
                }
            });

            AccountDTO accountDTO1 = accountService.queryAccountByUid(accountDTO.getUid());

            Assert.assertTrue(accountDTO1.isEnabled());
        });

    }

    @Test
    public void testDisabled(){
        PandaDomainTest.testInDB(()->{
            accountService.disabled(
                    new ArrayList(){
                        {
                            add(accountDTO.getUid());
                        }
                    }
            );
            AccountDTO accountDTO1 = accountService.queryAccountByUid(accountDTO.getUid());
            Assert.assertFalse(accountDTO1.isEnabled());
        });


    }

    @Test
    public void testQueryAccount(){

        PandaDomainTest.testInDB(()->{
            List<AccountDTO> list = accountService.queryAccount("");
            Assert.assertTrue(list.size() == 1);
            list = accountService.queryAccount("18620501006");
            Assert.assertTrue(list.size() == 1);
        });


    }

    @Test
    public void testQueryAccount2(){
        PandaDomainTest.testInDB(()->{
            PandaPage<AccountDTO> accountDTOPandaPage = accountService.queryAccountInPage("",1,10);
            Assert.assertTrue(accountDTOPandaPage.getValues().size() == 1);
        });

    }
}
