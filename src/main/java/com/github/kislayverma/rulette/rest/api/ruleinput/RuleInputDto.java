package com.github.kislayverma.rulette.rest.api.ruleinput;

import com.github.kislayverma.rulette.core.ruleinput.type.RuleInputType;

import java.io.Serializable;

public class RuleInputDto implements Serializable {
    private String name;
    private int priority;
    private RuleInputType ruleInputType;
    private String dataType;
    private String rangeLowerBoundFieldName;
    private String rangeUpperBoundFieldName;

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public RuleInputType getRuleInputType() {
        return ruleInputType;
    }

    public String getDataType() {
        return dataType;
    }

    public String getRangeLowerBoundFieldName() {
        return rangeLowerBoundFieldName;
    }

    public String getRangeUpperBoundFieldName() {
        return rangeUpperBoundFieldName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRuleInputType(RuleInputType ruleInputType) {
        this.ruleInputType = ruleInputType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setRangeLowerBoundFieldName(String rangeLowerBoundFieldName) {
        this.rangeLowerBoundFieldName = rangeLowerBoundFieldName;
    }

    public void setRangeUpperBoundFieldName(String rangeUpperBoundFieldName) {
        this.rangeUpperBoundFieldName = rangeUpperBoundFieldName;
    }

    @Override
    public String toString() {
        return "RuleInputDto{" +
            "name='" + name + '\'' +
            ", priority=" + priority +
            ", ruleInputType=" + ruleInputType +
            ", dataType='" + dataType + '\'' +
            ", rangeLowerBoundFieldName='" + rangeLowerBoundFieldName + '\'' +
            ", rangeUpperBoundFieldName='" + rangeUpperBoundFieldName + '\'' +
            '}';
    }
}
