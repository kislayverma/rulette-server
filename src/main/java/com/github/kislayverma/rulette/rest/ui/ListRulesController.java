package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import com.github.kislayverma.rulette.rest.rule.RuleDto;
import com.github.kislayverma.rulette.rest.rule.RuleService;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/ui")
public class ListRulesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListRulesController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/{ruleSystemName}/rules")
    public String showRulesForRuleSystem(Model model,
                                            @PathVariable String ruleSystemName,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "50") Integer pageSize) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(ruleSystemName);
            model.addAttribute("ruleSystem", rs.getMetaData());
            PaginatedResult<Rule> rulePage = ruleService.getRules(ruleSystemName, pageNum, pageSize);

            model.addAttribute("rulePage", rulePage);
            // This is the backing object for the rule evaluation modal
            model.addAttribute("ruleDto", new RuleDto());

            return "rules";
        } catch (Exception e) {
            throw new BadServerException("Error getting rules", e);
        }
    }

    @RequestMapping("/delete/{ruleSystemName}/{ruleId}")
    public String editRule(
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

    @RequestMapping(value="/eval/{ruleSystemName}", method = RequestMethod.POST)
    public String showApplicableRule(
        Model model,
        @ModelAttribute("ruleDto") RuleDto ruleDto,
        @PathVariable String ruleSystemName,
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "50") Integer pageSize,
        RedirectAttributes redirectAttributes) {
        try {
            LOGGER.info("Evaluating inputs to show applicable rule...");
            final RuleSystem rs = ruleSystemService.getRuleSystem(ruleSystemName);
            final Rule rule =
                ruleService.getApplicableRule(ruleSystemName, ruleDto.getRuleInputs());
            if (rule != null) {
                Map<String, String> ruleValueMap = TransformerUtil.convertToRawValueMap(rs, rule);
                RuleDto dto = new RuleDto();
                dto.setRuleInputs(ruleValueMap);
                model.addAttribute("ruleFound", dto);
            } else {
                redirectAttributes.addFlashAttribute("message", "No rule found for this input");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to update error", e);
            redirectAttributes.addFlashAttribute("message", "Error occurred : " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/ui/" + ruleSystemName + "/rules";
    }
}
