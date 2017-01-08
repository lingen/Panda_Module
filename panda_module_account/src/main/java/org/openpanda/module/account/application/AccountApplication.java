package org.openpanda.module.account.application;

import org.openpanda.microservice.ddd.domain.PandaPage;
import org.openpanda.module.account.dto.AccountDTO;

import java.util.List;

/**
 * Created by lingen on 2016/12/21.
 */
public interface AccountApplication {

    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO queryAccountByUid(String uid);

    AccountDTO queryAccountByUsername(String username);

    AccountDTO queryAccountByEmail(String email);

    AccountDTO queryAccountByMobile(String mobile);

    boolean changePwd(String uid,String oldPwd,String newPwd);

    boolean validPwd(String userId,String pwd);

    boolean enabled(List<String> uids);

    boolean disabled(List<String> uids);

    boolean delete(String uid);

    List<AccountDTO> queryAccount(String key);

    PandaPage<AccountDTO> queryAccountInPage(String key, int page, int pagesize);

}
