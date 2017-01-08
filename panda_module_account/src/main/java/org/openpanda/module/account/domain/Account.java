package org.openpanda.module.account.domain;

import org.apache.commons.lang3.StringUtils;
import org.dayatang.domain.Criteria;
import org.dayatang.domain.EntityRepository;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.domain.QueryCriterion;
import org.openpanda.microservice.ddd.domain.PandaBaseEntity;
import org.openpanda.module.account.AccountResponseCode;
import org.openpanda.module.account.AccountRuntimeException;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by lingen on 2016/12/21.
 */

@Entity
@Table(name = "panda_account_")
public class Account extends PandaBaseEntity {

    @Column(name = "username_",unique=true)
    private String username;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "mobile_",unique = true)
    private String mobile;

    @Column(name = "email_",unique = true)
    private String email;

    @Column(name = "password_")
    private String password;

    @Column(name = "last_login_time_")
    private long lastLoginTime;

    @Column(name = "login_error_count_")
    private int loginErrorCount;

    @Column(name = "enabled_")
    private boolean enabled;

    @Column(name = "account_type_")
    private AccountType accountType;

    @Column(name = "third_username_")
    private String thirdUsername;

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThirdUsername() {
        return thirdUsername;
    }

    public void setThirdUsername(String thirdUsername) {
        this.thirdUsername = thirdUsername;
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

    public static EntityRepository getEntityRepository(){
        return InstanceFactory.getInstance(EntityRepository.class);
    }


    //===========领域行为=============


    public void save(){

        if (username==null && email == null && mobile == null){
            throw new AccountRuntimeException(AccountResponseCode.Account_Params_Not_Valid);
        }

        if (StringUtils.isEmpty(password)){
            throw new AccountRuntimeException(AccountResponseCode.Account_Password_Empty);
        }

        Account existAccount = Account.queryByUsername(username);

        if (existAccount!=null){
            throw new AccountRuntimeException(AccountResponseCode.Account_Username_Exists);

        }

        existAccount = Account.queryByMobile(mobile);
        if (existAccount !=null ){
            throw new AccountRuntimeException(AccountResponseCode.Account_Mobile_Exists);
        }


        existAccount = Account.queryByEmail(email);
        if (existAccount != null){
            throw new AccountRuntimeException(AccountResponseCode.ACCOUNT_Email_Exists);
        }

        setPassword(getMD5(getPassword()));

        getEntityRepository().save(this);
    }
    public static Account createAccountByUsername(String username,String password,String nickname){
        Account account = new Account();
        account.username = username;
        account.password = password;
        account.nickname = nickname;
        account.enabled = true;
        account.accountType = AccountType.Local_Username;
        return account;
    }


    public static Account createAccountByMobile(String mobile,String password,String nickname){

        Account account = new Account();
        account.mobile = mobile;
        account.password = password;
        account.nickname = nickname;
        account.enabled = true;
        account.accountType = AccountType.Local_Mobile;
        return account;
    }

    public static Account createAccountByEmail(String email,String password,String nickname){
        Account account = new Account();
        account.email = email;
        account.password = password;
        account.nickname = nickname;
        account.enabled = true;
        account.accountType = AccountType.Local_Email;
        return account;
    }

    public static Account queryByUid(String uid){
        if (StringUtils.isEmpty(uid)){
            return null;
        }
        return getEntityRepository().get(Account.class,uid);
    }

    public static List<Account> findAll(){
        return getEntityRepository().createCriteriaQuery(Account.class).eq("deleted",false).list();
    }

    public boolean delete(){
        this.setDeleted(true);
        getEntityRepository().save(this);
        return true;
    }

    public static Account queryByUsername(String username){
        if (username==null){
            return null;
        }
        List<Account> accounts =  getEntityRepository().createCriteriaQuery(Account.class).eq("username",username).list();
        if (accounts.size() > 0 ) {
            return accounts.get(0);
        }
        return null;
    }

    public static Account queryByMobile(String mobile){
        if (mobile==null){
            return null;
        }
        List<Account> accounts =  getEntityRepository().createCriteriaQuery(Account.class).eq("mobile",mobile).list();
        if (accounts.size() > 0 ) {
            return accounts.get(0);
        }
        return null;
    }

    public static Account queryByEmail(String email){
        if (email==null){
            return null;
        }
        List<Account> accounts =  getEntityRepository().createCriteriaQuery(Account.class).eq("email",email).list();
        if (accounts.size() > 0 ) {
            return accounts.get(0);
        }
        return null;
    }

    private static Account queryByUidOrUsernameOrMobileOrEmail(String userId){
        if (StringUtils.isEmpty(userId)){
            return null;
        }

        QueryCriterion queryCriterion = Criteria.or(Criteria.eq("email", userId),Criteria.eq("uid", userId), Criteria.eq("username", userId),Criteria.eq("mobile", userId));


        return getEntityRepository().createCriteriaQuery(Account.class).and(queryCriterion).singleResult();
    }


    public static boolean validPassword(String userId,String password){

        if (StringUtils.isEmpty(password)){
            return false;
        }

        Account account = queryByUidOrUsernameOrMobileOrEmail(userId);

        if (account == null){
            return false;
        }

        if (account.getPassword().equals(getMD5(password))){
            return true;
        }
        return false;
    }


    public static boolean changePwd(String uid,String oldPwd,String newPwd){
        Account account = Account.queryByUid(uid);
        if (account!=null && account.getPassword().equals(getMD5(oldPwd))){
            account.password = getMD5(newPwd);
            getEntityRepository().save(account);
            return true;
        }
        return false;
    }


    public boolean enabled(){
        this.enabled = true;
        getEntityRepository().save(this);
        return true;
    }

    public boolean disabled(){
        this.enabled = false;
        getEntityRepository().save(this);
        return true;
    }

     static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密出现错误");
        }
    }
}
