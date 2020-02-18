package com.sps.data.controller;

import com.sps.data.entities.Paragraph;
import com.sps.data.repositories.ParagraphRepository;
import com.sps.data.util.AttachmentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    private ParagraphRepository paragraphRepository;

    @Autowired
    private AttachmentUtil attachmentUtil;

    @RequestMapping
    public ResponseEntity<Page> list(ModelMap model) {
       return paginationList(1,model);
    }
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<Page> paginationList(@PathVariable Integer pageNumber, ModelMap model) {

        PageRequest pageRequest = new PageRequest(pageNumber - 1, 20);
        Page<Paragraph> currentResults = paragraphRepository.findAll(pageRequest);

        model.put("users", currentResults);

        return new ResponseEntity<Page>(currentResults,HttpStatus.OK);
    }

    @RequestMapping(value = "/chapter-wise", method = RequestMethod.GET)
    public ResponseEntity<List<Paragraph>> paginationList(@RequestParam(value = "chapter", required = false) Long chapterId) {

        List<Paragraph> currentResults = paragraphRepository.findByChapterId(chapterId);

        return new ResponseEntity<List<Paragraph>>(currentResults,HttpStatus.OK);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<List<Paragraph>> create(@RequestBody List<Paragraph> paragraphs) {
        for (Paragraph attachment: paragraphs) {
            if(attachment.getHasImage() == true && attachment.getImage() != null) {
                try {
                    attachment.setFileName(attachmentUtil.saveAttachmentWithoutExtension(attachment));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
        paragraphs = (List<Paragraph>) paragraphRepository.saveAll(paragraphs);

       return new ResponseEntity<List<Paragraph>>(paragraphs,HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Paragraph> edit(@PathVariable("id") Long id) {
       Optional<Paragraph> assessee = paragraphRepository.findById(id);
       if(assessee == null){
           return new ResponseEntity<Paragraph>(HttpStatus.NO_CONTENT);
       }else {
           return new ResponseEntity<Paragraph>(assessee.get(),HttpStatus.OK);
       }
    }

}
