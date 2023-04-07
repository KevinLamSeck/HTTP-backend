package fr.aelion.streamer.dto.simplerDtos;

import fr.aelion.streamer.enumFolder.MemberType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private int id;

    private String lastName;

    private String firstName;

    private String email;

    private String phoneNumber;
    private MemberType role;

    private String login;

    private String password;
}
