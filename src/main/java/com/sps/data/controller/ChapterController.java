package com.sps.data.controller;

import com.sps.data.entities.Chapter;
import com.sps.data.entities.Section;
import com.sps.data.repositories.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chapter")
public class ChapterController {

    @Autowired
    private ChapterRepository chapterRepository;

    @RequestMapping
    public ResponseEntity<Page> list(ModelMap model) {
       return paginationList(1,model);
    }
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<Page> paginationList(@PathVariable Integer pageNumber, ModelMap model) {

        PageRequest pageRequest = new PageRequest(pageNumber - 1, 20);
        Page<Chapter> currentResults = chapterRepository.findAll(pageRequest);
        return new ResponseEntity<Page>(currentResults,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Chapter> create(@RequestBody Chapter chapter) {
        chapter = chapterRepository.save(chapter);
       return new ResponseEntity<Chapter>(chapter,HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Chapter> edit(@PathVariable("id") Long id) {
       Chapter assessee = chapterRepository.findOne(id);
       if(assessee == null){
           return new ResponseEntity<Chapter>(HttpStatus.NO_CONTENT);
       }else {
           return new ResponseEntity<Chapter>(assessee,HttpStatus.OK);
       }
    }

    @RequestMapping("/find-all")
    public ResponseEntity<List<Chapter>> findAll() {
        List<Chapter> sectionList = chapterRepository.findAll();
        return new ResponseEntity<List<Chapter>>(sectionList,HttpStatus.OK);
    }

}
