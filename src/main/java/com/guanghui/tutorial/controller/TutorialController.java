package com.guanghui.tutorial.controller;

import com.guanghui.tutorial.exception.ResourceNotFoundException;
import com.guanghui.tutorial.model.Tutorial;
import com.guanghui.tutorial.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tutorials")
public class TutorialController {
    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping()
    public ResponseEntity<List<Tutorial>> getAll(@RequestParam(required = false) String title, @RequestParam(required = false) String description) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();
            if (title != null)
                tutorials.addAll(tutorialRepository.findByTitleContainingIgnoreCase(title));
            else if (description != null)
                tutorials.addAll(tutorialRepository.getByDescriptionNativeParam(description));
//                tutorials.addAll(tutorialRepository.getByDescription(description));
            else
                tutorials.addAll(tutorialRepository.findAll());

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public Tutorial getTutorialById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Optional<Tutorial> tutorialDb = tutorialRepository.findById(id);

        if (tutorialDb.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }

        return tutorialDb.get();
    }

    @PostMapping()
    public Tutorial createTutorial(@RequestBody Tutorial tutorial) {
        return tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
    }

    @PutMapping("{id}")
    public Tutorial updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) throws ResourceNotFoundException {
        Optional<Tutorial> tutorialDb = tutorialRepository.findById(id);

        if (tutorialDb.isEmpty()) {
            throw new ResourceNotFoundException(id);
        } else {
            Tutorial t = tutorialDb.get();

            String title = tutorial.getTitle();
            if (Objects.nonNull(title) && !title.equals("")) {
                t.setTitle(title);
            }

            String description = tutorial.getDescription();
            if (Objects.nonNull(description) && !description.equals("")) {
                t.setDescription(description);
            }

            Boolean published = tutorial.getPublished();
            t.setPublished(published);

            return tutorialRepository.save(t);
        }
    }

    @DeleteMapping("{id}")
    public void deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);
    }

    @DeleteMapping("")
    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }

    @GetMapping("/published")
    public List<Tutorial> findByPublished() {
        return tutorialRepository.findByPublished(true);
    }
}
