package fr.aelion.streamer.dto.simplerDtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Getter
@Setter
public class CourseDto {
    private int id;
    private String title;
    private Boolean published = false;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String objective;

    private List<ModuleDto> modules;

}
