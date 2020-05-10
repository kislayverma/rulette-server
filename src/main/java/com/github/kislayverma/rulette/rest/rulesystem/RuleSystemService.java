package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.data.IDataProvider;
import com.github.kislayverma.rulette.core.exception.RuleConflictException;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.exception.BadClientException;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.exception.RuleSystemNotFoundException;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import com.github.kislayverma.rulette.rest.provider.DataProviderService;
import com.github.kislayverma.rulette.rest.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleSystemService {
    @Autowired
    private RuleSystemFactory ruleSystemFactory;
    @Autowired
    private DataProviderService providerService;

    public RuleSystem getRuleSystem(final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName);
    }

    public PaginatedResult<RuleSystemMetaData> getAllRuleSystemMetaData(int pageNum, int pageSize) {
        final List<RuleSystemMetaData> metaDataList = new ArrayList<>();
        ruleSystemFactory.getAllRuleSystems().forEach(rs -> {
            try {
                metaDataList.add(rs.getMetaData());
            } catch (Exception ex) {
                if (!(ex instanceof RuntimeException)) {
                    throw new BadServerException("Error returning rule system metadata", ex);
                } else {
                    throw (RuntimeException)ex;
                }
            }
        });

        PaginatedResult<RuleSystemMetaData> result = PaginationUtil.getPaginatedData(metaDataList, pageNum, pageSize);

        return result;
    }

    public RuleSystemMetaData getRuleSystemMetadata(String ruleSystemName) {
        try {
            return getRuleSystem(ruleSystemName).getMetaData();
        } catch (Exception ex) {
            if (!(ex instanceof RuntimeException)) {
                throw new BadServerException("Error returning rule system metadata", ex);
            } else {
                throw (RuntimeException)ex;
            }
        }
    }

    public void reload(String ruleSystemName) {
        try {
            getRuleSystem(ruleSystemName).reload();
        } catch (RuleConflictException ex) {
            throw new BadServerException("Error reloading rule system", ex);
        }
    }

    public void deleteRuleSystem(String ruleSystemName) {
        if (ruleSystemName == null || ruleSystemName.trim().isEmpty()) {
            throw new BadClientException("No rule system name give to delete");
        }
        try {
            IDataProvider provider = providerService.getProviderForRuleSystem(ruleSystemName);
            provider.deleteRuleSystem(ruleSystemName);
            ruleSystemFactory.deleteRuleSystem(ruleSystemName);
        } catch (Exception ex) {
            throw new BadServerException("Error deleting rule system", ex);
        }
    }
}
