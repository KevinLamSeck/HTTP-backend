package fr.aelion.streamer.dto.conceptorDtos;

import fr.aelion.streamer.dto.FullCourseDto;
import fr.aelion.streamer.dto.conceptorDtos.MediaDto;
import fr.aelion.streamer.dto.conceptorDtos.ModuleDto;
import fr.aelion.streamer.enumFolder.MemberType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudentDto {

    private int id;

    private String lastName;

    private String firstName;

    private String email;

    private String phoneNumber;
    private MemberType role;

    private String login;

    private String password;
}
