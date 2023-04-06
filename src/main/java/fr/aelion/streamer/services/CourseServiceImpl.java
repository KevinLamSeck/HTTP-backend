package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.*;
import fr.aelion.streamer.entities.Course;
import fr.aelion.streamer.entities.CourseToModule;
import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Module;
import fr.aelion.streamer.repositories.CourseRepository;
import fr.aelion.streamer.repositories.CourseToModuleRepository;
import fr.aelion.streamer.repositories.MediaRepository;
import fr.aelion.streamer.repositories.ModuleRepository;
import fr.aelion.streamer.services.interfaces.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository repository;
    @Autowired
    private CourseToModuleRepository courseToModuleRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<FullCourseDto> findAll() {
        var fullCourses = repository.findAll()
                .stream()
                .map(c -> {
                    var fullCourseDto = modelMapper.map(c, FullCourseDto.class);
                    return fullCourseDto;
                })
                .collect(Collectors.toList());
        // Compute media time
        for (FullCourseDto fc : fullCourses) {
            for (Module m : fc.getModuleList()) {
                ModuleDto mDTO = modelMapper.map(m, ModuleDto.class);
                List<MediaDto> allMedias = new ArrayList<MediaDto>();
                for (Media medias : mDTO.getMediaList()) {
                    allMedias.add(modelMapper.map(medias, MediaDto.class));
                }
                mDTO.setTotalTime(convertToTime(allMedias));
            }
        }
        return fullCourses;
    }

    @Override
    public FullCourseDto findOne(int id) {
        return repository.findById(id)
                .map((c) -> {
                    var fullCourseDto = modelMapper.map(c, FullCourseDto.class);
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

        newCourse = repository.save(newCourse);

        if (course.getModules().size() > 0) {
            Course finalNewCourse = newCourse;
            Set<CourseToModule> courseModules = new HashSet<>();

            int i = 0;
            for (ModuleAddDto mDto : course.getModules()) {
                var module = modelMapper.map(mDto, Module.class);

                //creer la table lien entre le cours et chaque module
                CourseToModule courseToModule = new CourseToModule();
                courseToModule.setModule(module);
                courseToModule.setCourse(modelMapper.map(course, Course.class));
                courseToModule.setOrderModule(i);
                i++;

                List<CourseToModule> cTm = new ArrayList<CourseToModule>();
                cTm.add(courseToModule);
                module.setCourses(cTm);
                module = moduleRepository.save(module);
                cTm.add(courseToModuleRepository.save(courseToModule));
                courseModules.add(courseToModule);
            }

//            course.getModules().forEach((mDto -> {
//
//            });
            finalNewCourse.setModules(courseModules);
        }
        return modelMapper.map(newCourse, FullCourseDto.class);
    }

    private String convertToTime(List<MediaDto> medias) {
        Float time = medias.stream()
                .map(m -> {
                    m.setTotalTime(LocalTime.MIN.plusSeconds(m.getDuration().longValue()).toString());
                    return m;
                })
                .map(m -> m.getDuration())
                .reduce(Float.valueOf(0), (subtotal, duration) -> subtotal + duration);

        var timeAsLong = Math.round(time);

        return LocalTime.MIN.plusSeconds(timeAsLong).toString();

    }
}
