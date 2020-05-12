package com.github.kislayverma.rulette.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rulesystems")
public class RuleSystemConfigList {
    private List<DataProviderConfigDto> dataproviders;

    public List<DataProviderConfigDto> getDataproviders() {
        return dataproviders;
    }

    public void setDataproviders(List<DataProviderConfigDto> dataproviders) {
        this.dataproviders = dataproviders;
    }

    @Override
    public String toString() {
        return "RuleSystemConfigList{" +
            "dataproviders=" + dataproviders +
            '}';
    }
}
