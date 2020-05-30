package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.api.rule.RuleDto;
import com.github.kislayverma.rulette.rest.api.rule.RuleService;
import com.github.kislayverma.rulette.rest.api.rulesystem.RuleSystemService;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/ui")
public class AddRuleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddRuleController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/provider/{providerName}/rulesystem/{ruleSystemName}/rule/show-add")
    public String showAddRule(Model model,
                              @PathVariable String providerName,
                              @PathVariable String ruleSystemName) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(providerName, ruleSystemName);
            model.addAttribute("ruleSystem", TransformerUtil.transformToDto(rs.getMetaData(), providerName));
            model.addAttribute("ruleDto", new RuleDto());
        } catch (Exception e) {
            LOGGER.error("Error showing add rule form", e);
            model.addAttribute("message", "Error showing add rule form - " + e.getMessage());
            model.addAttribute("alertClass", "alert-danger");
        }

        return "add-rule";
    }

    @RequestMapping(value="/provider/{providerName}/rulesystem/{ruleSystemName}/rule/add", method = RequestMethod.POST)
    public String addRule(
        Model model,
        @ModelAttribute("ruleDto") RuleDto ruleDto,
        @PathVariable String providerName,
        @PathVariable String ruleSystemName,
        RedirectAttributes redirectAttributes) {
        try {
            LOGGER.info("Adding rule to rule system {} with data : {}", ruleSystemName, ruleDto);
            Rule addedRule = ruleService.addRule(providerName, ruleSystemName, ruleDto.getRuleInputs());
            redirectAttributes.addFlashAttribute("message", "Successfully added rule!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/ui/provider/" + providerName + "/rulesystem/" + ruleSystemName + "/rule?ruleId=" + addedRule.getId();
        } catch (Exception e) {
            LOGGER.error("Failed to update rule", e);
            redirectAttributes.addFlashAttribute("message", "Error adding rule. " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/ui/provider/" + providerName + "/rulesystem/" + ruleSystemName + "/rule/show-add";
        }

    }
}
