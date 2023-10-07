package com.example.polls.controller;;

import com.example.polls.dto.PlanificationsDTO;
import com.example.polls.service.PlanificationsService;
import com.example.polls.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/planificationss")
public class PlanificationsController {

    private final PlanificationsService planificationsService;

    public PlanificationsController(final PlanificationsService planificationsService) {
        this.planificationsService = planificationsService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("planificationses", planificationsService.findAll());
        return "planifications/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("planifications") final PlanificationsDTO planificationsDTO) {
        return "planifications/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("planifications") @Valid final PlanificationsDTO planificationsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "planifications/add";
        }
        planificationsService.create(planificationsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("planifications.create.success"));
        return "redirect:/planificationss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("planifications", planificationsService.get(id));
        return "planifications/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("planifications") @Valid final PlanificationsDTO planificationsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "planifications/edit";
        }
        planificationsService.update(id, planificationsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("planifications.update.success"));
        return "redirect:/planificationss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = planificationsService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            planificationsService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("planifications.delete.success"));
        }
        return "redirect:/planificationss";
    }

}
