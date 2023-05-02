package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.*;
import fr.aelion.streamer.dto.CRUDDto.CourseUpdateDto;
import fr.aelion.streamer.dto.simplerDtos.CourseDto;
import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.dto.simplerDtos.MemberDto;
import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import fr.aelion.streamer.entities.*;
import fr.aelion.streamer.entities.Module;
import fr.aelion.streamer.repositories.*;
import fr.aelion.streamer.services.interfaces.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private CourseRepository repository;
    @Autowired
    private CourseToModuleRepository courseToModuleRepository;
    @Autowired
    private ModuleToMediaRepository moduleToMediaRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ConvertDtoService convertDtoService;

    public List<CourseDto> findAll() {
        var fullCourses = repository.findAll()
                .stream()
                .map(c -> {
                    //var fullCourseDto = modelMapper.map(c, FullCourseDto.class);
                    var fullCourseDto = convertDtoService.convertCourseToDto(c);
                    return fullCourseDto;
                })
                .collect(Collectors.toList());
        // Compute media time
//        for (FullCourseDto fc : fullCourses) {
//            for (ModuleDto mDTO : fc.getModules()) {
//
//                List<MediaDto> allMedias = new ArrayList<MediaDto>();
//
//                for (MediaDto medias : mDTO.getMedias()) {
//                    allMedias.add(medias);
//                }
//                mDTO.setTotalTime(convertToTime(allMedias));
//            }
//        }
        return fullCourses;
    }

    @Override
    public CourseDto findOne(int id) {
        return repository.findById(id)
                .map((c) -> {
                    var fullCourseDto = convertDtoService.convertCourseToDto(c);

                    return fullCourseDto;
                })
                .orElseThrow();

    }

    @Override
    public void remove(int id) {
        // Préambule : récupérer le cours dans son intégralité
        var oCourse = repository.findById(id);

        if (oCourse.isPresent()) {
            // 1. Update all modules to null course
            for (CourseToModule cTm : oCourse.get().getModules()) {
                //retirer le CTM de la list des cours dde chaque module
                Module changedModule = cTm.getModule();
                List<CourseToModule> lstCtm = new ArrayList<CourseToModule>();
                for (CourseToModule ctmInModule : cTm.getModule().getCourses()) {
                    if (ctmInModule != cTm) {
                        lstCtm.add(ctmInModule);
                    }
                }
                changedModule.setCourses(lstCtm);//update le module avec la nouvelle liste de liaison
                moduleRepository.save(changedModule);//save dans la bdd


                courseToModuleRepository.delete(cTm);//supprime les liaisons vide
            }

            // 3. Remove course
            repository.delete(oCourse.get());
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public FullCourseDto add(CourseAddDto course) {
        var newCourse = new Course();
        newCourse.setTitle(course.getTitle());
        newCourse.setObjective(course.getObjective());
        ////System.out.println(course.getCreator().getId());
        Member creator = new Member();
        creator.setId(course.getCreator().getId());
        newCourse.setCreator(creator);


        newCourse = repository.save(newCourse);

        if (course.getModules().size() > 0) {
            Course finalNewCourse = newCourse;
            List<CourseToModule> courseModules = new ArrayList<>();


            for (ModuleAddDto mDto : course.getModules()) {
                Module newModule = modelMapper.map(mDto, Module.class);
                newModule.setId(null);
                //creer le module
                newModule.setCreator(creator);
                newModule = moduleRepository.save(newModule);

                //creer la table lien entre le cours et chaque module
                CourseToModule courseToModule = new CourseToModule();
                courseToModule.setCourse(newCourse);
                courseToModule.setModule(newModule);
                courseToModule.setOrderModule(mDto.getOrder());
                courseToModuleRepository.save(courseToModule);


                if (mDto.getMedias() != null) {
                    for (MediaDto mediaDto : mDto.getMedias()) {
                        Media newMedia = modelMapper.map(mediaDto, Media.class);
                        newMedia.setCreator(course.getCreator());
                        newMedia.setId(null);
                        newMedia.setCreatedAt(LocalDate.now());
                        newMedia = mediaRepository.save(newMedia);


                        ModuleToMedia moduleToMedia = new ModuleToMedia();
                        moduleToMedia.setMedia(newMedia);
                        moduleToMedia.setModule(newModule);
                        moduleToMedia.setOrderMedia(mediaDto.getOrder());
                        moduleToMediaRepository.save(moduleToMedia);


                    }
                }

                //List<CourseToModule> cTm = new ArrayList<CourseToModule>();
                //cTm.add(courseToModule);

                //cTm.add();
                //courseModules.add(courseToModule);
            }

//            course.getModules().forEach((mDto -> {
//
//            });
            // finalNewCourse.setModules(courseModules);
        }
        return modelMapper.map(newCourse, FullCourseDto.class);
    }

    @Override
    public FullCourseDto update(CourseUpdateDto course) {

        var newCourse = new Course();
        newCourse.setId(course.getId());
        //System.out.println(newCourse.getId());
        newCourse.setTitle(course.getTitle());
        newCourse.setObjective(course.getObjective());
        newCourse.setCreator(course.getCreator());
        newCourse.setPublished(course.getPublished());
        newCourse.setUpdatedAt(LocalDate.now());


        newCourse = repository.save(newCourse);
        //System.out.println(course.getModules().size());
        List<ModuleDto> courseModules = new ArrayList<>();
        List<CourseToModule> cTmOld = courseToModuleRepository.getCourseToModuleByCourseId(course.getId());
        for (CourseToModule courseToModule : cTmOld) {

            courseToModuleRepository.delete(courseToModule);
        }



        if (course.getModules().size() > 0) {
            for (ModuleUpdateDto mDto : course.getModules()) {
//                //delete old cTm (a refactoriser)
                mDto.setCreator((course.getCreator() != null) ? modelMapper.map(course.getCreator(), MemberDto.class) : null);

                mDto.setId(null);

                ModuleDto mod = moduleService.add(mDto);

                courseModules.add(mod);
                //creer la table lien entre le cours et chaque module
                CourseToModule courseToModule = new CourseToModule();
                courseToModule.setCourse(newCourse);
                courseToModule.setModule(modelMapper.map(mod, Module.class));
                courseToModule.setOrderModule(mDto.getOrder());
                courseToModuleRepository.save(courseToModule);


            }
        } else {
            courseToModuleRepository.deleteAll(cTmOld);
        }
        FullCourseDto returnedCourse = modelMapper.map(newCourse, FullCourseDto.class);
        returnedCourse.setModules(courseModules);
        return returnedCourse;
    }


}
