package org.openpanda.module.account.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingen on 2016/12/24.
 */
public class AccountStatusVO {

    private List<String> uids;

    private String status;

    public List<String> getUids() {
        if (uids == null){
            uids = new ArrayList<>();
        }
        return uids;
    }

    public void setUids(List<String> uids) {

        this.uids = uids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
