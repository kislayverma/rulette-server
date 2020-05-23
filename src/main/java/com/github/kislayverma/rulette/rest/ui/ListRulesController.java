package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import com.github.kislayverma.rulette.rest.api.rule.RuleService;
import com.github.kislayverma.rulette.rest.api.rulesystem.RuleSystemMetadataDto;
import com.github.kislayverma.rulette.rest.api.rulesystem.RuleSystemService;
import com.github.kislayverma.rulette.rest.utils.PaginationUtil;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;

@Controller
@RequestMapping("/ui")
public class ListRulesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListRulesController.class);
    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "50";

    @Autowired
    private RuleSystemService ruleSystemService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/provider/{providerName}/rulesystem/{ruleSystemName}/rule")
    public String showRulesForRuleSystem(Model model,
                                         @PathVariable String providerName,
                                         @PathVariable String ruleSystemName,
                                         @RequestParam(required = false) String ruleId,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNum,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                         RedirectAttributes redirectAttributes) {
        return (ruleId == null || ruleId.trim().isEmpty()) ?
            showAllRules(model, providerName, ruleSystemName, pageNum, pageSize, redirectAttributes) :
            searchRuleById(model, providerName, ruleSystemName, ruleId);
    }

    private String showAllRules(
        Model model, String providerName, String ruleSystemName, Integer pageNum, Integer pageSize, RedirectAttributes redirectAttributes) {
        LOGGER.info("Showing all rules...");

        try {
            final RuleSystemMetaData rsmd = ruleSystemService.getRuleSystemMetadata(providerName, ruleSystemName);
            RuleSystemMetadataDto dto = TransformerUtil.transformToDto(rsmd, providerName);
            PaginatedResult<Rule> rulePage = ruleService.getRules(providerName, ruleSystemName, pageNum, pageSize);
            populateListRulePageModel(model, dto, rulePage, "");

            return "rules";
        } catch (Exception e) {
            LOGGER.error("Error loading rules", e);
            redirectAttributes.addFlashAttribute("message", "Failed to load rules. " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/ui/";
        }
    }

    private String searchRuleById(Model model, String providerName, String ruleSystemName, String ruleId) {
        LOGGER.info("Searching rule id {} of rule system {}", ruleId, ruleSystemName);
        final RuleSystemMetaData rsmd = ruleSystemService.getRuleSystemMetadata(providerName, ruleSystemName);
        RuleSystemMetadataDto dto = TransformerUtil.transformToDto(rsmd, providerName);

        try {
            final Rule rule = ruleService.getRuleById(providerName, ruleSystemName, ruleId);

            PaginatedResult<Rule> rulePage = PaginationUtil.getPaginatedData(
                Collections.singletonList(rule), Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_NUMBER));

            populateListRulePageModel(model, dto, rulePage, ruleId);

        } catch (Exception e) {
            LOGGER.error("Failed to get rule by id", e);
            model.addAttribute("message", "No rule found");
            model.addAttribute("alertClass", "alert-danger");
            PaginatedResult<Rule> rulePage = PaginationUtil.getPaginatedData(
                new ArrayList<>(), Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_NUMBER));
            populateListRulePageModel(model, dto, rulePage, ruleId);
        }

        return "rules";
    }

    private void populateListRulePageModel(Model model, RuleSystemMetadataDto rs, PaginatedResult<Rule> rulePage, String ruleId) {
        model.addAttribute("ruleSystem", rs);
        model.addAttribute("rulePage", rulePage);
        model.addAttribute("rulePage", rulePage);
        model.addAttribute("ruleId", ruleId);
    }

    @RequestMapping("/provider/{providerName}/rulesystem/{ruleSystemName}/rule/{ruleId}/delete")
    public String deleteRule(@PathVariable String providerName,
                             @PathVariable String ruleSystemName,
                             @PathVariable String ruleId,
                             RedirectAttributes redirectAttributes) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(providerName, ruleSystemName);

            LOGGER.info("Deleting rule id {} of rule system {}", ruleId, ruleSystemName);
            ruleService.deleteRule(providerName, ruleSystemName, ruleId);
            redirectAttributes.addFlashAttribute("message", "Successfully deleted rule!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            LOGGER.error("Failed to update error", e);
            redirectAttributes.addFlashAttribute("message", "Failed to delete rule");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/ui/provider/" + providerName + "/rulesystem/" + ruleSystemName + "/rule";
    }

}
