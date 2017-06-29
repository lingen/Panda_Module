package org.openpanda.module.sms;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.openpanda.module.sms.healthcheck.SmsHealthCheck;
import org.openpanda.module.sms.resources.SmsResources;

/**
 * Created by lingen on 2017/6/28.
 */
public class ProjectApplication extends Application<ProjectConfiguration> {
    @Override
    public void run(ProjectConfiguration projectConfiguration, Environment environment) throws Exception {

        final SmsHealthCheck smsHealthCheck = new SmsHealthCheck();
        environment.healthChecks().register("default",smsHealthCheck);

        environment.jersey().register(new SmsResources(projectConfiguration.getRedisConfig(),projectConfiguration.getApiKey(),projectConfiguration.getTemplate()));
    }

    public static void main(String[] args) throws Exception {
        new ProjectApplication().run(args);
    }
}
