package dev.seeruk.plugin.renewables.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {
    public AnvilTransitions anvilTransitions;
}
