package org.openpanda.module.account.domain;

import io.dropwizard.testing.DropwizardTestSupport;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openpanda.microservice.test.PandaDomainTest;
import org.openpanda.module.account.AccountRuntimeException;
import org.openpanda.module.account.Application;
import org.openpanda.module.account.Configuration;

import java.util.List;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

/**
 * Created by lingen on 2016/12/21.
 */
public class TestAccount {

    public static final DropwizardTestSupport<Configuration> ACCOUNT_SUPPORT =
            new DropwizardTestSupport<>(Application.class, resourceFilePath("test-config.yaml"));

    @BeforeClass
    public static void setUp() {
        ACCOUNT_SUPPORT.before();
    }

    @AfterClass
    public static void tearDown() {
        ACCOUNT_SUPPORT.after();
    }

    @Test
    public void testCreateAccountByUsername() {
        PandaDomainTest.testInDB(()->{
            Account account = Account.createAccountByUsername("lingen","lingen","aaa");
            account.save();
            Account account1 = Account.queryByUid(account.getUid());
            Assert.assertTrue(account1.getUsername().equals(account.getUsername()));
        });
    }

    @Test
    public void testCreateAccountByMobile(){
        PandaDomainTest.testInDB(()->{
            Account account = Account.createAccountByMobile("18620501006","lingen","御剑");
            account.save();
            Account account1 = Account.queryByUid(account.getUid());
            Assert.assertTrue(account1.getMobile().equals(account.getMobile()));
        });
    }

    @Test
    public void testCreateAccountByEmail(){
        PandaDomainTest.testInDB(()->{
            Account account = Account.createAccountByEmail("lingen@foxmail.com","lingen","御剑");
            account.save();
            Account account1 = Account.queryByUid(account.getUid());
            Assert.assertTrue(account.getEmail().equals(account1.getEmail()));
        });
    }

    @Test
    public void testFindAll(){
        PandaDomainTest.testInDB(()->{
            Account account = Account.createAccountByUsername("lingen","lingen","aaa");
            account.save();
            List<Account> accounts = Account.findAll();
            Assert.assertTrue(accounts.size() == 1);
        });
    }

    @Test
    public void testDelete(){
        PandaDomainTest.testInDB(()->{
            Account account = Account.createAccountByUsername("lingen","lingen","aaa");
            account.save();
            List<Account> accounts = Account.findAll();
            Assert.assertTrue(accounts.size() == 1);
            account.delete();
            accounts = Account.findAll();
            Assert.assertTrue(accounts.size() == 0);
        });
    }

    @Test(expected = AccountRuntimeException.class)
    public void testNotAllowedCreateAccount(){
        PandaDomainTest.testInDB(()->{
            Account account = new Account();
            account.save();
        });
    }

    @Test
    public void testValidPassword(){
        PandaDomainTest.testInDB(()->{
            Account account = Account.createAccountByMobile("18620501006","lingen","御剑");
            account.save();
            Assert.assertTrue(account.validPassword(account.getUid(),"lingen"));
            Assert.assertTrue(account.validPassword(account.getMobile(),"lingen"));
            Assert.assertFalse(account.validPassword(account.getUsername(),"lingen"));
        });

    }

    @Test
    public void testChangePwd(){
        PandaDomainTest.testInDB(()->{

            Account account = Account.createAccountByMobile("18620501006","lingen","御剑");
            account.save();
            boolean success = Account.changePwd(account.getUid(),"lingen","lingen1");
            Assert.assertTrue(success);
            Assert.assertTrue(account.validPassword(account.getUid(),"lingen1"));
            Assert.assertFalse(account.validPassword(account.getUid(),"lingen"));

        });

    }


    @Test
    public void testEnableOrDisable(){
        PandaDomainTest.testInDB(()->{
            Account account = Account.createAccountByMobile("18620501006","lingen","御剑");
            account.save();

            account.disabled();
            Account account1 = Account.queryByUid(account.getUid());

            Assert.assertFalse(account1.isEnabled());

            account.enabled();
            account1 = Account.queryByUid(account.getUid());
            Assert.assertTrue(account1.isEnabled());

        });
    }

}
