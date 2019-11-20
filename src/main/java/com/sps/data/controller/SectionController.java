package com.sps.data.controller;

import com.sps.data.entities.Content;
import com.sps.data.entities.Section;
import com.sps.data.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    @Autowired
    private SectionRepository sectionRepository;

    @RequestMapping
    public ResponseEntity<ModelMap> list(ModelMap model) {
       return paginationList(1,model);
    }
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<ModelMap> paginationList(@PathVariable Integer pageNumber, ModelMap model) {

        PageRequest pageRequest = new PageRequest(pageNumber - 1, 20);
        Page<Section> currentResults = sectionRepository.findAll(pageRequest);


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
    public ResponseEntity<Section> create(@RequestBody Section section, HttpRequest request) {

        section = sectionRepository.save(section);

       return new ResponseEntity<Section>(section,HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Section> edit(@PathVariable("id") Long id) {
       Optional<Section> section = sectionRepository.findById(id);
       if(section == null){
           return new ResponseEntity<Section>(HttpStatus.NO_CONTENT);
       }else {
           return new ResponseEntity<Section>(section.get(),HttpStatus.OK);
       }
    }

    @RequestMapping("/find-all")
    public ResponseEntity<List<Section>> findAll() {
       List<Section> sectionList = sectionRepository.findAll();
        return new ResponseEntity<List<Section>>(sectionList,HttpStatus.OK);
    }

}
