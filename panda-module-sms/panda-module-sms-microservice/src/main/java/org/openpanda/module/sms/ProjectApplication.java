package org.openpanda.module.sms;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Created by lingen on 2017/6/28.
 */
public class ProjectApplication extends Application<ProjectConfiguration> {
    @Override
    public void run(ProjectConfiguration projectConfiguration, Environment environment) throws Exception {

        System.out.println(projectConfiguration);
    }

    public static void main(String[] args) throws Exception {
        new ProjectApplication().run(args);
    }
}
