package fr.aelion.streamer.dto.conceptorDtos;

import fr.aelion.streamer.enumFolder.MemberType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConceptorDto {

    private int id;

    private String lastName;

    private String firstName;

    private String email;

    private String phoneNumber;
    private MemberType role;

    private List<CourseDto> courses = new ArrayList<CourseDto>();

    private List<ModuleDto> modules =new ArrayList<ModuleDto>();

    private List<MediaDto> medias=new ArrayList<MediaDto>();

    private String login;

    private String password;
}
