package fr.aelion.streamer.dto;

import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
