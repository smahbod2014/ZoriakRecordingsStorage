package com.zoriak;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class AppConfiguration extends Configuration {

    @NotNull
    @JsonProperty("version")
    private String version;
}
