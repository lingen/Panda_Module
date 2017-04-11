package org.openpanda.module.account.application;

import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.lang3.StringUtils;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openpanda.microservice.ddd.domain.PandaPage;
import org.openpanda.module.account.AccountResponseCode;
import org.openpanda.module.account.AccountRuntimeException;
import org.openpanda.module.account.domain.Account;
import org.openpanda.module.account.dto.AccountAssembly;
import org.openpanda.module.account.dto.AccountDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingen on 2016/12/21.
 */
public class AccountApplicationImpl implements AccountApplication {

    public static final String Account_Find_All = "from Account where deleted = false";

    public static final String Account_Find_By_Search = "from Account where (username like :key or email like :key or mobile like :key) and deleted = false ";
    @Inject
    private QueryChannelService queryChannelService;

    @UnitOfWork
    public AccountDTO createAccount(AccountDTO accountDTO){
        Account account = AccountAssembly.toAccount(accountDTO);
        account.setEnabled(true);
        account.save();
        return AccountAssembly.toAccountDTO(account);
    }

    @UnitOfWork
    public  AccountDTO queryAccountByUid(String uid){
        Account account = Account.queryByUid(uid);
        return AccountAssembly.toAccountDTO(account);
    }

    @UnitOfWork
    public AccountDTO queryAccountByUsername(String username){
        Account account = Account.queryByUsername(username);
        return AccountAssembly.toAccountDTO(account);
    }

    @UnitOfWork
    public AccountDTO queryAccountByEmail(String email){
        Account account = Account.queryByEmail(email);
        return AccountAssembly.toAccountDTO(account);
    }

    @UnitOfWork
    public AccountDTO queryAccountByMobile(String mobile){
        Account account = Account.queryByMobile(mobile);
        return AccountAssembly.toAccountDTO(account);
    }

    @UnitOfWork
    public boolean changePwd(String uid,String oldPwd,String newPwd){
        return Account.changePwd(uid,oldPwd,newPwd);
    }

    @UnitOfWork
    public  boolean validPwd(String uid,String pwd){
        return Account.validPassword(uid,pwd);
    }

    @UnitOfWork
    public boolean enabled(List<String> uids){
        boolean success = false;
        for (String uid:uids){
            Account account = Account.queryByUid(uid);
            success = account.enabled();
            if (!success){
                throw new AccountRuntimeException(AccountResponseCode.Account_Status_Change_Fail);
            }
        }
        return success;
    }

    @UnitOfWork
    public boolean disabled(List<String> uids){
        boolean success = false;
        for (String uid:uids){
            Account account = Account.queryByUid(uid);
            success = account.disabled();
            if (!success){
                throw new AccountRuntimeException(AccountResponseCode.Account_Status_Change_Fail);
            }
        }
        return success;
    }

    @UnitOfWork
    public boolean delete(String uid){
        Account account = Account.queryByUid(uid);
        return account.delete();
    }

    @UnitOfWork
    public List<AccountDTO> queryAccount(String key){
        List<Account> accounts;

        if (StringUtils.isEmpty(key)){
            accounts = queryChannelService.createJpqlQuery(Account_Find_All).list();
        }else{
            accounts = queryChannelService.createJpqlQuery(Account_Find_By_Search).addParameter("key","%"+key+"%").list();
        }
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for(Account account:accounts){
            accountDTOList.add(AccountAssembly.toAccountDTO(account));
        }
        return accountDTOList;
    }

    @UnitOfWork
    public PandaPage<AccountDTO> queryAccountInPage(String key,int page,int pagesize){
        Page<Account> pageResult;
        int pageIndex = pagesize * ( page - 1 );

        if (StringUtils.isEmpty(key)){
            pageResult = queryChannelService.createJpqlQuery(Account_Find_All).setPage(pageIndex,pagesize).pagedList();
        }else{
            pageResult = queryChannelService.createJpqlQuery(Account_Find_By_Search).addParameter("key","%"+key+"%").setPage(pageIndex,pagesize).pagedList();
        }

        List<AccountDTO> accountDTOList = new ArrayList<>();
        for(Account account:pageResult.getData()){
            accountDTOList.add(AccountAssembly.toAccountDTO(account));
        }

        return new PandaPage<>(pageResult.getResultCount(),pagesize,(int)pageResult.getPageCount(),page,accountDTOList);
    }
}
