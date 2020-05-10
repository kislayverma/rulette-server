package com.github.kislayverma.rulette.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rulesystems")
public class RuleSystemConfigList {
    private List<RuleSystemConfigDto> systems;
    private List<DataProviderConfigDto> dataproviders;

    @Override
    public String toString() {
        return "RuleSystemConfigList{" +
            "systems=" + systems +
            ", dataproviders=" + dataproviders +
            '}';
    }

    public List<RuleSystemConfigDto> getSystems() {
        return systems;
    }

    public void setSystems(List<RuleSystemConfigDto> systems) {
        this.systems = systems;
    }

    public List<DataProviderConfigDto> getDataproviders() {
        return dataproviders;
    }

    public void setDataproviders(List<DataProviderConfigDto> dataproviders) {
        this.dataproviders = dataproviders;
    }
}
