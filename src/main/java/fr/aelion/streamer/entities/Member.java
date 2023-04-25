package fr.aelion.streamer.entities;

import fr.aelion.streamer.enumFolder.MemberType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lastName;

    private String firstName;

    private String email;

    private String phoneNumber;
    private MemberType role;

    @OneToMany(mappedBy = "creator")
    private List<Course> courses;

    @OneToMany(mappedBy = "creator")
    private List<Module> modules;

    @OneToMany(mappedBy = "creator")
    private List<Media> medias;

    private String login;

    private String password;
}
