package com.github.kislayverma.rulette.rest.api.rulesystem;

import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;

import java.io.Serializable;
import java.util.List;

public class RuleSystemMetadataDto implements Serializable {
    private String ruleSystemName;
    private String tableName;
    private List<RuleInputMetaData> inputColumnList;
    private String uniqueIdColumnName;
    private String uniqueOutputColumnName;
    private String providerName;

    @Override
    public String toString() {
        return "RuleSystemMetadataDto{" +
            "ruleSystemName='" + ruleSystemName + '\'' +
            ", tableName='" + tableName + '\'' +
            ", inputColumnList=" + inputColumnList +
            ", uniqueIdColumnName='" + uniqueIdColumnName + '\'' +
            ", uniqueOutputColumnName='" + uniqueOutputColumnName + '\'' +
            ", providerName='" + providerName + '\'' +
            '}';
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getRuleSystemName() {
        return ruleSystemName;
    }

    public void setRuleSystemName(String ruleSystemName) {
        this.ruleSystemName = ruleSystemName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<RuleInputMetaData> getInputColumnList() {
        return inputColumnList;
    }

    public void setInputColumnList(List<RuleInputMetaData> inputColumnList) {
        this.inputColumnList = inputColumnList;
    }

    public String getUniqueIdColumnName() {
        return uniqueIdColumnName;
    }

    public void setUniqueIdColumnName(String uniqueIdColumnName) {
        this.uniqueIdColumnName = uniqueIdColumnName;
    }

    public String getUniqueOutputColumnName() {
        return uniqueOutputColumnName;
    }

    public void setUniqueOutputColumnName(String uniqueOutputColumnName) {
        this.uniqueOutputColumnName = uniqueOutputColumnName;
    }
}
