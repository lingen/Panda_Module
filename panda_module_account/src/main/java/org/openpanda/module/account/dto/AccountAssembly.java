package org.openpanda.module.account.dto;

import org.apache.commons.lang3.StringUtils;
import org.openpanda.module.account.domain.Account;

import java.util.UUID;

/**
 * Created by lingen on 2016/12/21.
 */
public class AccountAssembly {

    public static Account toAccount(AccountDTO accountDTO){
        if (accountDTO == null){
            return null;
        }
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setAccountType(accountDTO.getAccountType());
        account.setCreateTime(accountDTO.getCreateTime());
        account.setEmail(accountDTO.getEmail());
        account.setMobile(accountDTO.getMobile());
        account.setEnabled(accountDTO.isEnabled());
        account.setLastLoginTime(accountDTO.getLastLoginTime());
        account.setLoginErrorCount(accountDTO.getLoginErrorCount());
        account.setNickname(accountDTO.getNickname());
        account.setPassword(accountDTO.getPassword());
        account.setUpdateTime(accountDTO.getUpdateTime());
        account.setThirdUsername(accountDTO.getThirdUsername());
        if (StringUtils.isEmpty(accountDTO.getUid())){
            account.setUid(UUID.randomUUID().toString());
        }else{
            account.setUid(accountDTO.getUid());
        }
        return account;
    }

    public static AccountDTO toAccountDTO(Account account){
        if (account == null){
            return null;
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(account.getUsername());
        accountDTO.setUid(account.getUid());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setCreateTime(account.getCreateTime());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setMobile(account.getMobile());
        accountDTO.setEnabled(account.isEnabled());
        accountDTO.setLastLoginTime(accountDTO.getLastLoginTime());
        accountDTO.setUpdateTime(accountDTO.getUpdateTime());
        accountDTO.setLoginErrorCount(accountDTO.getLoginErrorCount());
        accountDTO.setNickname(accountDTO.getNickname());
        accountDTO.setUpdateTime(accountDTO.getUpdateTime());
        accountDTO.setThirdUsername(accountDTO.getThirdUsername());
        return accountDTO;
    }
}
