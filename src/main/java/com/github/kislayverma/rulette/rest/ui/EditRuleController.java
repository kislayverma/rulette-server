package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.rule.RuleDto;
import com.github.kislayverma.rulette.rest.rule.RuleService;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
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
public class EditRuleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditRuleController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/edit/{ruleSystemName}/{ruleId}")
    public String showEditRule(Model model, @PathVariable String ruleSystemName, @PathVariable String ruleId) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(ruleSystemName);
            model.addAttribute("ruleSystem", rs.getMetaData());
            Map<String, String> ruleValueMap =
                TransformerUtil.convertToRawValueMap(rs, ruleService.getRuleById(ruleSystemName, ruleId));
            model.addAttribute("ruleToEdit", ruleValueMap);

            RuleDto dto = new RuleDto();
            dto.setRuleInputs(ruleValueMap);
            model.addAttribute("ruleDto", dto);

            return "edit-rule";
        } catch (Exception e) {
            throw new BadServerException("Error getting rules", e);
        }
    }

    // Refer for details on how to show the success/failure messages :
    // https://stackoverflow.com/questions/46744586/thymeleaf-show-a-success-message-after-clicking-on-submit-button
    // https://stackoverflow.com/questions/53683804/how-to-add-success-notification-after-form-submit
    @RequestMapping(value="/save/{ruleSystemName}/{ruleId}", method = RequestMethod.POST)
    public String saveEditedRule(
        @ModelAttribute("ruleDto") RuleDto ruleDto,
        @PathVariable String ruleSystemName,
        @PathVariable String ruleId,
        RedirectAttributes redirectAttributes) {
        try {
            final RuleSystem rs = ruleSystemService.getRuleSystem(ruleSystemName);
            final Map<String, String> inputMap = ruleDto.getRuleInputs();

            LOGGER.info("Updating rule id {} of rule system {} with data : {}", ruleId, ruleSystemName, inputMap);
            ruleService.updateRule(ruleSystemName, ruleId, inputMap);
            redirectAttributes.addFlashAttribute("message", "Successfully updated rule!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            LOGGER.error("Failed to update error", e);
            redirectAttributes.addFlashAttribute("message", "Failed to update rule");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/ui/" + ruleSystemName + "/rules";
    }
}
