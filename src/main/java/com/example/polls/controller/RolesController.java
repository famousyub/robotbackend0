package com.example.polls.controller;;


import com.example.polls.dto.RolesDTO;
import com.example.polls.model.User;
import com.example.polls.repository.UserRepository;
import com.example.polls.service.RolesService;
import com.example.polls.util.CustomCollectors;
import com.example.polls.util.WebUtils;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/roless")
public class RolesController {

    private final RolesService rolesService;
    private final UserRepository userRepository;

    public RolesController(final RolesService rolesService, final UserRepository userRepository) {
        this.rolesService = rolesService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getEmail)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("roleses", rolesService.findAll());
        return "roles/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("roles") final RolesDTO rolesDTO) {
        return "roles/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("roles") @Valid final RolesDTO rolesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "roles/add";
        }
        rolesService.create(rolesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("roles.create.success"));
        return "redirect:/roless";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("roles", rolesService.get(id));
        return "roles/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("roles") @Valid final RolesDTO rolesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "roles/edit";
        }
        rolesService.update(id, rolesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("roles.update.success"));
        return "redirect:/roless";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        rolesService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("roles.delete.success"));
        return "redirect:/roless";
    }

}
