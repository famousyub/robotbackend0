package com.example.polls.controller;


import com.example.polls.dto.ExcutionDTO;
import com.example.polls.service.ExcutionService;
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
@RequestMapping("/excutions")
public class ExcutionController {

    private final ExcutionService excutionService;

    public ExcutionController(final ExcutionService excutionService) {
        this.excutionService = excutionService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("excutions", excutionService.findAll());
        return "excution/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("excution") final ExcutionDTO excutionDTO) {
        return "excution/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("excution") @Valid final ExcutionDTO excutionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "excution/add";
        }
        excutionService.create(excutionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("excution.create.success"));
        return "redirect:/excutions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("excution", excutionService.get(id));
        return "excution/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("excution") @Valid final ExcutionDTO excutionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "excution/edit";
        }
        excutionService.update(id, excutionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("excution.update.success"));
        return "redirect:/excutions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = excutionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            excutionService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("excution.delete.success"));
        }
        return "redirect:/excutions";
    }

}
