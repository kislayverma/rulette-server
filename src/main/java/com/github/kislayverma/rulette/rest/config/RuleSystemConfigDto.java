package com.github.kislayverma.rulette.rest.config;

public class RuleSystemConfigDto {
    private String provider;
    private String name;

    @Override
    public String toString() {
        return "RuleSystemConfigDto{" +
            "provider='" + provider + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
