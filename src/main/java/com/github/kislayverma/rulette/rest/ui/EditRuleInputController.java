package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;
import com.github.kislayverma.rulette.rest.ruleinput.RuleInputDto;
import com.github.kislayverma.rulette.rest.ruleinput.RuleInputService;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
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

@Controller
@RequestMapping("/ui")
public class EditRuleInputController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditRuleInputController.class);

    @Autowired
    private RuleInputService ruleInputService;

    @Autowired
    private RuleSystemService ruleSystemService;

    @RequestMapping("/{ruleSystemName}/ruleinput/add")
    public String showAddRuleInput(
        Model model, @PathVariable String ruleSystemName) {
        RuleInputDto ruleInputToAdd = new RuleInputDto();
        model.addAttribute("ruleSystem", ruleSystemService.getRuleSystemMetadata(ruleSystemName));
        model.addAttribute("ruleInputToAdd", ruleInputToAdd);

        return "rule-input";
    }

    @RequestMapping(value="/{ruleSystemName}/ruleinput/save", method= RequestMethod.POST)
    public String addRuleInput(
        @ModelAttribute("ruleInputToAdd") RuleInputDto ruleInputDto, @PathVariable String ruleSystemName, RedirectAttributes redirectAttributes) {
        try {
            LOGGER.info(ruleInputDto.toString());
            RuleInputMetaData rimd = new RuleInputMetaData(
                ruleInputDto.getName(),
                ruleInputDto.getPriority(),
                ruleInputDto.getRuleInputType(),
                ruleInputDto.getDataType(),
                ruleInputDto.getRangeLowerBoundFieldName(),
                ruleInputDto.getRangeUpperBoundFieldName());
            ruleInputService.addRuleInput(ruleSystemName, rimd);

            redirectAttributes.addFlashAttribute("message", "Successfully added rule input! Reload the rule system to deploy the new state");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/ui";
        } catch (Exception e) {
            LOGGER.error("Error saving new rule input", e);
            redirectAttributes.addFlashAttribute("message", "Error adding rule input! " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/ui/{ruleSystemName}/ruleinput/add";
        }
    }

    @RequestMapping("/{ruleSystemName}/ruleinput/{ruleInputName}/delete")
    public String deleteRuleInput(
        Model model, @PathVariable String ruleSystemName, @PathVariable String ruleInputName, RedirectAttributes redirectAttributes) {
        ruleInputService.deleteRuleInput(ruleSystemName, ruleInputName);
        redirectAttributes.addFlashAttribute("message", "Successfully deleted rule input! Reload the rule system to deploy the new state");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/ui";
    }
}
