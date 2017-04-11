package org.openpanda.module.account.resource;

import io.dropwizard.testing.DropwizardTestSupport;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openpanda.microservice.ddd.domain.PandaPage;
import org.openpanda.module.account.Application;
import org.openpanda.module.account.Configuration;
import org.openpanda.module.account.vo.ChangePwdVO;
import org.openpanda.module.account.dto.AccountDTO;
import org.openpanda.module.account.vo.AccountStatusVO;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

/**
 * Created by lingen on 2016/12/24.
 */
public class TestAccountResource {

    private static String HTTP_LOCAL_SERVER = "http://localhost:";

    private static AccountDTO accountDTO;

    public static final DropwizardTestSupport<Configuration> ACCOUNT_SUPPORT =
            new DropwizardTestSupport<>(Application.class, resourceFilePath("test-config.yaml"));

    @BeforeClass
    public static void setUp() {
        ACCOUNT_SUPPORT.before();
        prepareData();
    }

    @AfterClass
    public static void tearDown() {
        ACCOUNT_SUPPORT.after();
    }

    @Test
    public void testCreateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail("lingen@foxmail.com");
        accountDTO.setPassword("lingen123");

        Response response = ClientBuilder.newClient().target(
                HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts").request().post(Entity.entity(accountDTO, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response.getStatus() == 400);

    }

    @Test
    public void testQueryAccount(){
        Response response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/lingen@foxmail.com?type=email").request().get();
        Assert.assertTrue(response.getStatus() == 200);

        AccountDTO accountDTO = response.readEntity(AccountDTO.class);
        Assert.assertTrue(accountDTO.getUid() != null);
        Assert.assertTrue(accountDTO.getEmail().equals("lingen@foxmail.com"));

        response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/lingen@foxmail.com?type=uid").request().get();
        Assert.assertTrue(response.getStatus() == 400);
    }

    @Test
    public void testChangePwd(){
        Response response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/lingen@foxmail.com?type=email").request().get();
        Assert.assertTrue(response.getStatus() == 200);

        AccountDTO accountDTO = response.readEntity(AccountDTO.class);

        ChangePwdVO changePwdVO = new ChangePwdVO();
        changePwdVO.setOldPwd("lingen123");
        changePwdVO.setNewPwd("lingen111");

        Response response1 = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/"+accountDTO.getUid()+"/pwd")
                .request().put(Entity.entity(changePwdVO,MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response1.getStatus() == 200);
    }

    @Test
    public void testValidPwd(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail("lingen@foxmail.com");
        accountDTO.setPassword("lingen123");

        Response response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/pwd?type=email")
                .request().put(Entity.entity(accountDTO,MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response.getStatus() == 200);


        Response response2 = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/pwd?type=uid")
                .request().put(Entity.entity(accountDTO,MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response2.getStatus() == 400);
    }

    @Test
    public void testSearchAccount(){
        Response response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts")
                .request().get();

        List<AccountDTO> accountDTOList = response.readEntity(new GenericType<List<AccountDTO>>(){});
        Assert.assertTrue(accountDTOList.size() == 1);
    }

    @Test
    public void testSearchAccountInPage(){
        Response response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts?page=1&pagesize=5")
                .request().get();

        PandaPage<AccountDTO> accountDTOPandaPage = response.readEntity(new GenericType<PandaPage<AccountDTO>>(){});
        Assert.assertTrue(accountDTOPandaPage.getTotal() == 1);
    }

    @Test
    public void testSetStaus(){
        AccountStatusVO accountStatusVO = new AccountStatusVO();
        accountStatusVO.getUids().add(accountDTO.getUid());
        accountStatusVO.setStatus("disable");

        Response response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/status")
                .request().put(Entity.entity(accountStatusVO,MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response.getStatus() == 200);

        AccountStatusVO accountStatusVO1 = new AccountStatusVO();
        accountStatusVO.getUids().add(accountDTO.getUid());
        accountStatusVO.setStatus("enable");

        response = ClientBuilder.newClient().target(HTTP_LOCAL_SERVER + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts/status")
                .request().put(Entity.entity(accountStatusVO,MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response.getStatus() == 200);


    }

    private static void prepareData(){
        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setEmail("lingen@foxmail.com");
        accountDTO1.setPassword("lingen123");
        Response response = ClientBuilder.newClient().target(
                "http://localhost:" + ACCOUNT_SUPPORT.getLocalPort() + "/v1/accounts").request().post(Entity.entity(accountDTO1, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertTrue(response.getStatus() == 201);

        accountDTO = response.readEntity(AccountDTO.class);
        Assert.assertTrue(accountDTO.getUid() != null);
    }
}
