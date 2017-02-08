package com.javacodegeeks.examples.controller;

import com.javacodegeeks.examples.entities.Authority;
import com.javacodegeeks.examples.entities.User;
import com.javacodegeeks.examples.enumeration.Role;
import com.javacodegeeks.examples.repositories.AuthorityRepository;
import com.javacodegeeks.examples.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @RequestMapping
    public String list() {
        return "redirect:/user/page/1";
    }

    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView paginationList(@PathVariable Integer pageNumber) {

        Map<String, Object> model = new HashMap<String, Object>();
        PageRequest pageRequest = new PageRequest(pageNumber - 1, 20);
        Page<User> currentResults = userRepository.findAll(pageRequest);


        model.put("users", currentResults);

        // Pagination variables
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages());

        model.put("beginIndex", begin);
        model.put("endIndex", end);
        model.put("currentIndex", current);

        return new ModelAndView("user/list", model);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute User user) {
        return "user/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid User user, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("user/form", "formErrors",
                    result.getAllErrors());
        }

        user = this.userRepository.save(user);

        updateAuthority(user.getUsername(),user.getRoles());

        redirect.addFlashAttribute("globalMessage",
                "Successfully Created/Edited a new user");
        return new ModelAndView("redirect:/user/view/{user.id}",
                "user.id", user.getId());
    }

    private void updateAuthority(String username, List<Role> userRoles) {
        final List<Authority> existingAuthorities = authorityRepository.findAllByUsername(username);

        if (existingAuthorities != null) {
            authorityRepository.delete(existingAuthorities);
        }

        if (userRoles != null && userRoles.size() > 0) {
            for (Role role : userRoles) {
                Authority authority = new Authority();
                authority.setAuthority(role.toString());
                authority.setUsername(username);
                authorityRepository.save(authority);
            }
        }
    }

    @RequestMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") Integer id) {
        return new ModelAndView("user/view", "user",
                userRepository.findOne(id));
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id) {
        return new ModelAndView("user/form", "user",
                userRepository.findOne(id));
    }

}
