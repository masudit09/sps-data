package com.javacodegeeks.examples;

import com.javacodegeeks.examples.entities.Authority;
import com.javacodegeeks.examples.entities.User;
import com.javacodegeeks.examples.enumeration.Role;
import com.javacodegeeks.examples.repositories.AuthorityRepository;
import com.javacodegeeks.examples.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rana on 2/4/17.
 */
@RestController
@RequestMapping("/global/user/")
public class UserCreatorGlobal {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createUser(){
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
}
