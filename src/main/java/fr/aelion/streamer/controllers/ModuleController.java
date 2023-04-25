package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.ModuleAddDto;
import fr.aelion.streamer.dto.ModuleUpdateDto;
import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import fr.aelion.streamer.entities.*;
import fr.aelion.streamer.entities.Module;
import fr.aelion.streamer.repositories.*;
import fr.aelion.streamer.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/modules") // http://127.0.0.1:5000/api/v1/students
@CrossOrigin
public class ModuleController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseToModuleRepository courseToModuleRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private ModuleToMediaRepository mtmRepository;

    @GetMapping
    public List<ModuleDto> findAll() {
        return moduleService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findOne(@PathVariable int id) {
        try {
            return ResponseEntity.ok(moduleService.findOne(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Module with " + id + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("creator/{id}")
    public ResponseEntity<?> findByCreatorID(@PathVariable int id) {
        try {
            return ResponseEntity.ok(moduleService.findByCreatorID(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Module with " + id + " creator was not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ModuleDto> add(@RequestBody ModuleAddDto module) {
        ModuleDto moduleDto = this.moduleService.add(module);
        return ResponseEntity.ok(moduleDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody ModuleUpdateDto module) {
        try {
            return ResponseEntity.ok(moduleService.update(module));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        // Préambule : récupérer le module dans son intégralité
        var module = this.moduleRepository.findById(id);

        if (module.isPresent()) {
            // 1. Update all courses to null module
            for (CourseToModule cTm : module.get().getCourses()) {
                //retirer le CTM de la list des cours dde chaque module
                Course changedCourse = cTm.getCourse();
                List<CourseToModule> lstCtm = new ArrayList<CourseToModule>();
                for (CourseToModule ctmInModule : cTm.getCourse().getModules()) {
                    if (ctmInModule != cTm) {
                        lstCtm.add(ctmInModule);
                    }
                }
                changedCourse.setModules(lstCtm);//update le module avec la nouvelle liste de liaison
                courseRepository.save(changedCourse);//save dans la bdd


                courseToModuleRepository.delete(cTm);//supprime les liaisons vide
            }

            for (ModuleToMedia mtm : module.get().getMedias()) {
                //retirer le MTM
                Media changedMedia = mtm.getMedia();
                List<ModuleToMedia> lstMtm = new ArrayList<ModuleToMedia>();
                for (ModuleToMedia mtmInModule : mtm.getMedia().getModules()) {
                    if (mtmInModule != mtm) {
                        lstMtm.add(mtmInModule);
                    }
                }
                changedMedia.setModules(lstMtm);//update le module avec la nouvelle liste de liaison
                mediaRepository.save(changedMedia);//save dans la bdd


                mtmRepository.delete(mtm);//supprime les liaisons vide
            }


            // 3. Remove course
            moduleRepository.delete(module.get());
        } else {
            throw new NoSuchElementException();
        }
    }
}
