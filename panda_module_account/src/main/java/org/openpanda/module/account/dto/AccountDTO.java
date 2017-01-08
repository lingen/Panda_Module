package org.openpanda.module.account.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.openpanda.module.account.domain.AccountType;

/**
 * Created by lingen on 2016/12/21.
 */
public class AccountDTO {

    private String uid;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String username;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String nickname;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String mobile;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String email;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String password;

    private long lastLoginTime;

    private int loginErrorCount;

    private boolean enabled;

    private long createTime;

    private long updateTime;

    private AccountType accountType;

    private String thirdUsername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(int loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getThirdUsername() {
        return thirdUsername;
    }

    public void setThirdUsername(String thirdUsername) {
        this.thirdUsername = thirdUsername;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
