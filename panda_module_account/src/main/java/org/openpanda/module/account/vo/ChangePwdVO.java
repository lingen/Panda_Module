package org.openpanda.module.account.vo;

import java.io.Serializable;

/**
 * Created by lingen on 2016/12/24.
 */
public class ChangePwdVO implements Serializable{

    private String newPwd;

    private String oldPwd;


    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }
}
