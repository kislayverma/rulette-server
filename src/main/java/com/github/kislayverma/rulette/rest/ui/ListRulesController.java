package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.rule.RuleService;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui")
public class ListRulesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListRulesController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/{ruleSystemName}/rules")
    public String showAllRulesForRuleSystem(Model model, @PathVariable String ruleSystemName) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(ruleSystemName);
            model.addAttribute("ruleSystem", rs.getMetaData());
            model.addAttribute(
                "rules", TransformerUtil.convertToRawValues(rs, ruleService.getAllRules(ruleSystemName)));

            return "rules";
        } catch (Exception e) {
            throw new BadServerException("Error getting rules", e);
        }
    }
}
