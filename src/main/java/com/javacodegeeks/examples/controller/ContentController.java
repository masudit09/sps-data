package com.javacodegeeks.examples.controller;

import com.javacodegeeks.examples.entities.Content;
import com.javacodegeeks.examples.repositories.ContetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/assessee")
public class ContentController {

    @Autowired
    private ContetRepository assesseeRepository;

    @RequestMapping
    public ResponseEntity<ModelMap> list(ModelMap model) {
       return paginationList(1,model);
    }
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<ModelMap> paginationList(@PathVariable Integer pageNumber, ModelMap model) {

        PageRequest pageRequest = new PageRequest(pageNumber - 1, 20);
        Page<Content> currentResults = assesseeRepository.findAll(pageRequest);


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
    public ResponseEntity<Content> create(@RequestBody Content assessee, HttpRequest request) {

        assessee = assesseeRepository.save(assessee);

       return new ResponseEntity<Content>(assessee,HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Content> edit(@PathVariable("id") Long id) {
       Optional<Content> assessee = assesseeRepository.findById(id);
       if(assessee == null){
           return new ResponseEntity<Content>(HttpStatus.NO_CONTENT);
       }else {
           return new ResponseEntity<Content>(assessee.get(),HttpStatus.OK);
       }
    }

}
