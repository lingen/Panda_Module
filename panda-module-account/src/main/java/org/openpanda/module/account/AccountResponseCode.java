package org.openpanda.module.account;

/**
 * Created by lingen on 2016/12/22.
 */
public enum AccountResponseCode {

    Account_Status_Change_Fail{
        @Override
        public String description() {
            return "enable or disable accounts fail";
        }
    },

    Account_Params_Not_Valid {
        @Override
        public String description() {
            return "Params not valid";
        }
    },

    Account_Username_Exists {
        @Override
        public String description() {
            return  "Username already exists";
        }
    },

    ACCOUNT_Email_Exists {
        @Override
        public String description() {
            return "Email already exists";
        }
    },

    Account_Mobile_Exists {
        @Override
        public String description() {
            return "Mobile already exists";
        }
    },

    Account_Password_Empty {
        @Override
        public String description() {
            return "The Password is Empty";
        }
    },

    Account_User_Not_Exists{
        @Override
        public String description() {
            return "User Not Found";
        }
    };


    public abstract String description();

}
