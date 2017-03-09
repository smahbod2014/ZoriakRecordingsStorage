package com.zoriak;

import com.zoriak.resources.RecordingsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class StorageApplication extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new StorageApplication().run(args);
    }

    @Override
    public String getName() {
        return "Zoriak Recordings Storage";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {

    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new RecordingsResource());
        environment.jersey().register(new MultiPartFeature());
    }
}
