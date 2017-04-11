package org.openpanda.module.account;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import org.openpanada.microservice.cloud.etcd.CloudRegisterEtcd;
import org.openpanda.microservice.cloud.resiger.CloudRegister;
import org.openpanda.microservice.dropwizard.hibernate.PandaApplication;
import org.openpanda.module.account.application.AccountApplication;
import org.openpanda.module.account.application.AccountApplicationImpl;
import org.openpanda.module.account.domain.Account;
import org.openpanda.module.account.resource.AccountResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lingen on 2016/12/21.
 */
public class Application extends PandaApplication<Configuration> {


    public List<String> cloudServers(){
        return new ArrayList<>();
    }

    @Override
    public Class[] entities() {
        return new Class[]{
                Account.class
        };
    }

    public CloudRegister cloudRegister(){
        return new CloudRegisterEtcd();
    }

    @Override
    public PandaGuiceModule createModule() {
        return new PandaGuiceModule() {
            @Override
            public void bindIoc() {
                AccountApplication accountService = new UnitOfWorkAwareProxyFactory(hibernate).create(AccountApplicationImpl.class);
                bind(AccountApplication.class).toInstance(accountService);
            }
        };
    }

    @Override
    public Map<String,HealthCheck> healthCheckList() {
        return new HashMap(){};
    }

    @Override
    public List resources() {
        return new ArrayList(){
            {
                add(new AccountResource());
            }
        };
    }

    public static void main(String[] args) throws Exception {
        new Application().run(args);
    }

}
