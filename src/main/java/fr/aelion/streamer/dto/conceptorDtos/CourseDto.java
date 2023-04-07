package fr.aelion.streamer.dto.conceptorDtos;

import fr.aelion.streamer.entities.CourseToModule;
import fr.aelion.streamer.entities.Student;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

public class CourseDto {
    private int id;
    private String title;
    private Boolean published=false;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String objective;

    private Set<ModuleDto> modules;
}
