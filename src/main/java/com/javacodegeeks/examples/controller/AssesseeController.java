package com.javacodegeeks.examples.controller;

import com.javacodegeeks.examples.entities.Assessee;
import com.javacodegeeks.examples.entities.Authority;
import com.javacodegeeks.examples.entities.User;
import com.javacodegeeks.examples.enumeration.Role;
import com.javacodegeeks.examples.repositories.AssesseeRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/assessee")
public class AssesseeController {

    @Autowired
    private AssesseeRepository assesseeRepository;

    @RequestMapping
    public ResponseEntity<ModelMap> list(ModelMap model) {
       return paginationList(1,model);
    }
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<ModelMap> paginationList(@PathVariable Integer pageNumber, ModelMap model) {

        PageRequest pageRequest = new PageRequest(pageNumber - 1, 20);
        Page<Assessee> currentResults = assesseeRepository.findAll(pageRequest);


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

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<Assessee> create(@RequestBody Assessee assessee, HttpRequest request) {

        assessee = assesseeRepository.save(assessee);

       return new ResponseEntity<Assessee>(assessee,HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Assessee> edit(@PathVariable("id") Integer id) {
       Assessee assessee = assesseeRepository.findOne(id);
       if(assessee == null){
           return new ResponseEntity<Assessee>(HttpStatus.NO_CONTENT);
       }else {
           return new ResponseEntity<Assessee>(assessee,HttpStatus.OK);
       }
    }

}
