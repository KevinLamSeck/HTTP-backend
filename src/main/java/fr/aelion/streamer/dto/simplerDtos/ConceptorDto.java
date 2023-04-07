package fr.aelion.streamer.dto.simplerDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConceptorDto extends MemberDto {


    private List<CourseDto> courses = new ArrayList<CourseDto>();

    private List<ModuleDto> modules =new ArrayList<ModuleDto>();

    private List<MediaDto> medias=new ArrayList<MediaDto>();

}
