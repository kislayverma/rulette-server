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

import java.util.Map;

@Controller
@RequestMapping("/ui")
public class EvaluateInputsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluateInputsController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/provider/{providerName}/rulesystem/{ruleSystemName}/rule/evaluate")
    public String evaluate(
        Model model,
        @ModelAttribute("ruleDto") RuleDto ruleDto,
        @PathVariable String providerName,
        @PathVariable String ruleSystemName) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(providerName, ruleSystemName);
            model.addAttribute("ruleSystem", TransformerUtil.transformToDto(rs.getMetaData(), providerName));
            model.addAttribute("ruleDto", ruleDto);

            LOGGER.info("Evaluating inputs to show applicable rule. Input {}", ruleDto.getRuleInputs());
            final Rule rule = ruleService.getApplicableRule(providerName, ruleSystemName, ruleDto.getRuleInputs());

            RuleDto dto = null;

            if (rule != null) {
                LOGGER.info("Found applicable rule {}", rule);
                Map<String, String> ruleValueMap = TransformerUtil.convertToRawValueMap(rs, rule);
                dto = new RuleDto();
                dto.setRuleInputs(ruleValueMap);
                model.addAttribute("message", "Found matching rule!");
                model.addAttribute("alertClass", "alert-success");
            } else {
                LOGGER.error("No rule found for input");
                model.addAttribute("message", "No rule found");
                model.addAttribute("alertClass", "alert-danger");
            }

            model.addAttribute("applicableRule", rule);
        } catch (Exception e) {
            model.addAttribute("message", "Error getting rules. " + e.getMessage());
            model.addAttribute("alertClass", "alert-danger");
        }

        return "evaluate-input";
    }
}
