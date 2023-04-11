package fr.aelion.streamer.services.interfaces;

import fr.aelion.streamer.dto.CourseAddDto;
import fr.aelion.streamer.dto.FullCourseDto;
import fr.aelion.streamer.dto.simplerDtos.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> findAll();

    CourseDto findOne(int id);


    void remove(int id);

    FullCourseDto add(CourseAddDto course);
}
