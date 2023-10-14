package com.example.polls.controller;


import com.example.polls.model.Excution;
import com.example.polls.model.ExcutionT;
import com.example.polls.repository.ExcutionRepository;
import com.example.polls.repository.ExcutionTRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.CrossOrigin;
        import org.springframework.web.bind.annotation.DeleteMapping;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.PutMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/excutionts")
@CrossOrigin("*")
public class ExcutionTController {


    @Autowired
    ExcutionTRepository tutorialRepository;

    @Autowired
    private ExcutionRepository excutionRepository;

    @GetMapping("")
    public ResponseEntity<List<ExcutionT>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<ExcutionT> tutorials = new ArrayList<ExcutionT>();

            if (title == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExcutionT> getTutorialById(@PathVariable("id") long id) {
        Optional<ExcutionT> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<ExcutionT> createTutorial(@RequestBody ExcutionT tutorial) {
        try {
            tutorial.setRobotsId(Long.parseLong("1"));

            Excution exc = new Excution();

            exc.setDate(LocalDateTime.now());
            exc.setLog(tutorial.getLog());


            exc.setLog("log " + LocalDateTime.now().toString());
            exc.setLogger("logger" +  LocalDateTime.now().toString() + " .log");
            exc.setDateCreated(OffsetDateTime.now());
            exc.setLastUpdated(OffsetDateTime.now());
            excutionRepository.save(exc);
            ExcutionT _tutorial = tutorialRepository
                    .save(new ExcutionT(tutorial.getTitle(), tutorial.getLog(), true));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExcutionT> updateTutorial(@PathVariable("id") long id, @RequestBody ExcutionT tutorial) {
        Optional<ExcutionT> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            ExcutionT _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setLog(tutorial.getLog());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/exc")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/published")
    public ResponseEntity<List<ExcutionT>> findByPublished() {
        try {
            List<ExcutionT> tutorials = tutorialRepository.findByPublished(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}