package com.javacodegeeks.examples.controller;

import com.javacodegeeks.examples.entities.Authority;
import com.javacodegeeks.examples.entities.User;
import com.javacodegeeks.examples.enumeration.Role;
import com.javacodegeeks.examples.repositories.AuthorityRepository;
import com.javacodegeeks.examples.repositories.UserRepository;
import com.javacodegeeks.examples.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @RequestMapping
    public ResponseEntity<ModelMap> list(ModelMap model) {
       return paginationList(1,model);
    }
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<ModelMap> paginationList(@PathVariable Integer pageNumber, ModelMap model) {

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

        return new ResponseEntity<ModelMap>(model,HttpStatus.OK);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute User user) {
        return "user/form";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody User user, HttpRequest request) {
        /*if (result.hasErrors()) {
            return new ModelAndView("user/form", "formErrors",
                    result.getAllErrors());
        }*/

        user = this.userRepository.save(user);

        updateAuthority(user.getUsername(),user.getRoles());

       /* redirect.addFlashAttribute("globalMessage",
                "Successfully Created/Edited a new user");
        return new ModelAndView("redirect:/user/view/{user.id}",
                "user.id", user.getId());*/
       return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getAccount() {
        return new ResponseEntity<User>(userRepository.findByUsername(Util.getCurrentUsername()),HttpStatus.OK);
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

    @RequestMapping("/global/user/create")
    public String globalUserCreator() {
        User user = userRepository.findByUsername("admin");
        if (user == null) {
            user = new User();
            user.setUsername("admin");
            user.setFullName("admin");
            user.setFullNameBangla("admin");
            user.setPlainPassword("admin");
            user.setEnabled(true);
            userRepository.save(user);

            Authority authority = new Authority();
            authority.setAuthority(Role.ROLE_ADMINISTRATOR.toString());
            authority.setUsername("admin");
            authorityRepository.save(authority);
            return "Success";
        }else
        {
            return "User Admin Already Exist";
        }
    }

    @RequestMapping("/change-password")
    public ResponseEntity<String> edit(@RequestParam("curPass") String curPass, @RequestParam("newPass") String newPass) {
                User user = userRepository.findByUsername(Util.getCurrentUsername());
                if(Util.getEncoder().matches(curPass, user.getPassword())) {
                        user.setPassword(Util.getEncoder().encode(newPass));
                        userRepository.save(user);
                        return new ResponseEntity("Success", HttpStatus.OK);
                    }
        
                        return new ResponseEntity("Success", HttpStatus.NOT_ACCEPTABLE);
            }

}
