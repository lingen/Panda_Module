package org.openpanda.module.account.resource;

import com.codahale.metrics.annotation.Timed;
import org.dayatang.domain.InstanceFactory;
import org.openpanda.microservice.ddd.domain.PandaPage;
import org.openpanda.microservice.ddd.rule.PandaResourceExecuter;
import org.openpanda.module.account.AccountRuntimeException;
import org.openpanda.module.account.application.AccountApplication;
import org.openpanda.module.account.dto.AccountDTO;
import org.openpanda.module.account.vo.AccountStatusVO;
import org.openpanda.module.account.vo.ChangePwdVO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.openpanda.module.account.AccountResponseCode.*;

/**
 * Created by lingen on 2016/12/21.
 */
@Path("/v1/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @POST
    @Timed
    public Response create(AccountDTO accountDTO){

        return PandaResourceExecuter.execute(()->{

            AccountDTO accountDTO1 = getAccountService().createAccount(accountDTO);
            return Response.status(201).entity(accountDTO1).build();
        });
    }

    @GET
    @Timed
    @Path("/{userId}")
    public Response queryAccount(@PathParam("userId") String userId,@QueryParam("type") String type){

        return PandaResourceExecuter.execute(()->{
            AccountDTO accountDTO = null;

            if ("uid".equalsIgnoreCase(type)){
                accountDTO = getAccountService().queryAccountByUid(userId);
            }
            else if("mobile".equalsIgnoreCase(type)){
                accountDTO = getAccountService().queryAccountByMobile(userId);
            }
            else if("email".equalsIgnoreCase(type)){
                accountDTO = getAccountService().queryAccountByEmail(userId);
            }
            else if("username".equalsIgnoreCase(type)){
                accountDTO = getAccountService().queryAccountByUsername(userId);
            }

            if (accountDTO==null){
                throw new AccountRuntimeException(Account_User_Not_Exists);
            }

            return Response.status(200).entity(accountDTO).build();

        });

    }

    @Timed
    @PUT
    @Path("/{uid}/pwd")
    public Response changePwd(ChangePwdVO changePwdVO,@PathParam("uid") String uid){

        return PandaResourceExecuter.executeWithReturnBool(()->{
            boolean success = getAccountService().changePwd(uid,changePwdVO.getOldPwd(),changePwdVO.getNewPwd());
            return success;
        });
    }

    @Timed
    @PUT
    @Path("/pwd")
    public Response validPwd(@QueryParam("type") String type,AccountDTO accountDTO){
        return PandaResourceExecuter.executeWithReturnBool(()->{
            boolean success = false;
            if ("uid".equalsIgnoreCase(type)){
                success = getAccountService().validPwd(accountDTO.getUid(),accountDTO.getPassword());
            }
            else if("email".equalsIgnoreCase(type)){
                success = getAccountService().validPwd(accountDTO.getEmail(),accountDTO.getPassword());
            }
            else if("mobile".equalsIgnoreCase(type)){
                success = getAccountService().validPwd(accountDTO.getMobile(),accountDTO.getPassword());
            }
            else if("usename".equalsIgnoreCase(type)){
                success = getAccountService().validPwd(accountDTO.getUsername(),accountDTO.getPassword());
            }

            return success;
        });
    }

    @Timed
    @GET
    public Response searchAccount(@QueryParam("search") String search,@QueryParam("page") int page,@QueryParam("pagesize") int pagesize){

        return PandaResourceExecuter.execute(()->{
            if (page == 0 || pagesize == 0){
                List<AccountDTO> list =  getAccountService().queryAccount(search);
                return Response.status(200).entity(list).build();
            }else{
                PandaPage<AccountDTO> pandaPage = getAccountService().queryAccountInPage(search,page,pagesize);
                return Response.status(200).entity(pandaPage).build();
            }
        });
    }

    @Timed
    @PUT
    @Path("/status")
    public Response setStatus(AccountStatusVO accountStatusVO){
        return PandaResourceExecuter.executeWithReturnBool(()->{

            boolean success = false;
            if ("enable".equalsIgnoreCase(accountStatusVO.getStatus())){
                success = getAccountService().enabled(accountStatusVO.getUids());
            }
            else if("disable".equalsIgnoreCase(accountStatusVO.getStatus())){
                for (String uid:accountStatusVO.getUids()){
                    success = getAccountService().disabled(accountStatusVO.getUids());
                    if (!success){
                        return false;
                    }
                }
            }

            return success;
        });
    }

    private AccountApplication accountService;

    public AccountApplication getAccountService(){
        if (accountService == null){
            accountService = InstanceFactory.getInstance(AccountApplication.class);
        }
        return accountService;
    }
}
