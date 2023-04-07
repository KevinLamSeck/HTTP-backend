package fr.aelion.streamer.dto;

import fr.aelion.streamer.dto.conceptorDtos.ModuleDto;
import fr.aelion.streamer.entities.Course;
import fr.aelion.streamer.entities.CourseToModule;
import fr.aelion.streamer.entities.Module;
import fr.aelion.streamer.entities.ModuleToMedia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class FullCourseDto {
    private int id;
    private String title;
    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String objective;

    private List<ModuleDto> modules = new ArrayList<ModuleDto>();




}
