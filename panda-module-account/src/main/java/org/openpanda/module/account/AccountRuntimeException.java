package org.openpanda.module.account;


import org.openpanda.microservice.ddd.rule.PandaRuntimeException;

/**
 * Created by lingen on 2016/12/22.
 */
public class AccountRuntimeException extends PandaRuntimeException {

    public AccountRuntimeException(AccountResponseCode accountResponseCode){
        super(accountResponseCode.toString(),accountResponseCode.description());
    }

}
