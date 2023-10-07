package com.example.polls.controller;;


import com.example.polls.dto.RobotsDTO;
import com.example.polls.model.Excution;
import com.example.polls.model.Planifications;
import com.example.polls.model.User;
import com.example.polls.repository.ExcutionRepository;
import com.example.polls.repository.PlanificationRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.service.RobotsService;
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
@RequestMapping("/robotss")
public class RobotsController {

    private final RobotsService robotsService;
    private final UserRepository userRepository;
    private final PlanificationRepository planificationsRepository;
    private final ExcutionRepository excutionRepository;

    public RobotsController(final RobotsService robotsService, final UserRepository userRepository,
            final PlanificationRepository planificationsRepository,
            final ExcutionRepository excutionRepository) {
        this.robotsService = robotsService;
        this.userRepository = userRepository;
        this.planificationsRepository = planificationsRepository;
        this.excutionRepository = excutionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getEmail)));
        model.addAttribute("planificationValues", planificationsRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Planifications::getId, Planifications::getStatus)));
        model.addAttribute("excutionValues", excutionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Excution::getId, Excution::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("robotses", robotsService.findAll());
        return "robots/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("robots") final RobotsDTO robotsDTO) {
        return "robots/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("robots") @Valid final RobotsDTO robotsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("planification") && robotsDTO.getPlanification() != null && robotsService.planificationExists(robotsDTO.getPlanification())) {
            bindingResult.rejectValue("planification", "Exists.robots.planification");
        }
        if (!bindingResult.hasFieldErrors("excution") && robotsDTO.getExcution() != null && robotsService.excutionExists(robotsDTO.getExcution())) {
            bindingResult.rejectValue("excution", "Exists.robots.excution");
        }
        if (bindingResult.hasErrors()) {
            return "robots/add";
        }
        robotsService.create(robotsDTO,new User());
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("robots.create.success"));
        return "redirect:/robotss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("robots", robotsService.get(id));
        return "robots/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("robots") @Valid final RobotsDTO robotsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final RobotsDTO currentRobotsDTO = robotsService.get(id);
        if (!bindingResult.hasFieldErrors("planification") && robotsDTO.getPlanification() != null &&
                !robotsDTO.getPlanification().equals(currentRobotsDTO.getPlanification()) &&
                robotsService.planificationExists(robotsDTO.getPlanification())) {
            bindingResult.rejectValue("planification", "Exists.robots.planification");
        }
        if (!bindingResult.hasFieldErrors("excution") && robotsDTO.getExcution() != null &&
                !robotsDTO.getExcution().equals(currentRobotsDTO.getExcution()) &&
                robotsService.excutionExists(robotsDTO.getExcution())) {
            bindingResult.rejectValue("excution", "Exists.robots.excution");
        }
        if (bindingResult.hasErrors()) {
            return "robots/edit";
        }
        robotsService.update(id, robotsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("robots.update.success"));
        return "redirect:/robotss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        robotsService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("robots.delete.success"));
        return "redirect:/robotss";
    }

}
