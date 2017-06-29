package org.openpanda.module.sms.healthcheck;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by lingen on 2017/6/29.
 */
public class SmsHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {


        return Result.healthy();
    }
}
