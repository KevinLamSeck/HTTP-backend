package fr.aelion.streamer.dto;

import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.entities.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CourseAddDto {
    private String title;
    private String objective;
    private Member creator;
    private Set<ModuleAddDto> modules;



}
