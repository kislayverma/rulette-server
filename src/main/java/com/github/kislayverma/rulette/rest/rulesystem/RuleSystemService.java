package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import com.github.kislayverma.rulette.rest.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleSystemService {
    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    public RuleSystem getRuleSystem(final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName);
    }

    public PaginatedResult<RuleSystemMetaData> getAllRuleSystemMetaData(int pageNum, int pageSize) {
        final List<RuleSystemMetaData> metaDataList = new ArrayList<>();
        ruleSystemFactory.getAllRuleSystems().forEach(rs -> {
            try {
                metaDataList.add(rs.getMetaData());
            } catch (Exception ex) {
                throw new BadServerException("Error returning rule system metadata", ex);
            }
        });

        PaginatedResult<RuleSystemMetaData> result = PaginationUtil.getPaginatedData(metaDataList, pageNum, pageSize);

        return result;
    }

    public RuleSystemMetaData getRuleSystemMetadata(String ruleSystemName) {
        try {
            return getRuleSystem(ruleSystemName).getMetaData();
        } catch (Exception ex) {
            throw new BadServerException("Error returning rule system metadata", ex);
        }
    }

    public void reload(String ruleSystemName) {
        try {
            ruleSystemFactory.reloadRuleSystem(ruleSystemName);
        } catch (Exception ex) {
            throw new BadServerException("Error returning rule system metadata", ex);
        }
    }
}
