package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import com.github.kislayverma.rulette.rest.rule.RuleDto;
import com.github.kislayverma.rulette.rest.rule.RuleService;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import com.github.kislayverma.rulette.rest.utils.PaginationUtil;
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

    @RequestMapping("/{ruleSystemName}/rules")
    public String showRulesForRuleSystem(Model model,
                                            @PathVariable String ruleSystemName,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNum,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                            RedirectAttributes redirectAttributes) {
        try {
            final RuleSystemMetaData rs = ruleSystemService.getRuleSystemMetadata(ruleSystemName);
            PaginatedResult<Rule> rulePage = ruleService.getRules(ruleSystemName, pageNum, pageSize);
            populateListRulePageModel(model, rs, rulePage, "");

            return "rules";
        } catch (Exception e) {
            LOGGER.error("Error loading rules", e);
            redirectAttributes.addFlashAttribute("message", "Failed to load rules. " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/ui/";
        }
    }

    @RequestMapping("/{ruleSystemName}")
    public String searchRuleById(
        Model model,
        @PathVariable String ruleSystemName,
        @RequestParam(required = false) String ruleId) {
        if (ruleId == null || ruleId.trim().isEmpty()) {
            LOGGER.info("Empty ruleId, so showing all rules...");
            return "redirect:/ui/" + ruleSystemName + "/rules";
        }

        final RuleSystemMetaData rs = ruleSystemService.getRuleSystemMetadata(ruleSystemName);

        try {

            LOGGER.info("Searching rule id {} of rule system {}", ruleId, ruleSystemName);
            final Rule rule = ruleService.getRuleById(ruleSystemName, ruleId);

            PaginatedResult<Rule> rulePage = PaginationUtil.getPaginatedData(
                Collections.singletonList(rule), Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_NUMBER));

            populateListRulePageModel(model, rs, rulePage, ruleId);

        } catch (Exception e) {
            LOGGER.error("Failed to get rule by id", e);
            model.addAttribute("message", "No rule found");
            model.addAttribute("alertClass", "alert-danger");
            PaginatedResult<Rule> rulePage = PaginationUtil.getPaginatedData(
                new ArrayList<>(), Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_NUMBER));
            populateListRulePageModel(model, rs, rulePage, ruleId);
        }

        return "rules";
    }

    private void populateListRulePageModel(Model model, RuleSystemMetaData rs, PaginatedResult<Rule> rulePage, String ruleId) {
        model.addAttribute("ruleSystem", rs);
        model.addAttribute("rulePage", rulePage);
        model.addAttribute("rulePage", rulePage);
        model.addAttribute("ruleId", ruleId);
    }

    @RequestMapping("/delete/{ruleSystemName}/{ruleId}")
    public String deleteRule(
        @PathVariable String ruleSystemName,
        @PathVariable String ruleId,
        RedirectAttributes redirectAttributes) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(ruleSystemName);

            LOGGER.info("Deleting rule id {} of rule system {}", ruleId, ruleSystemName);
            ruleService.deleteRule(ruleSystemName, ruleId);
            redirectAttributes.addFlashAttribute("message", "Successfully deleted rule!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            LOGGER.error("Failed to update error", e);
            redirectAttributes.addFlashAttribute("message", "Failed to delete rule");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/ui/" + ruleSystemName + "/rules";
    }
}
