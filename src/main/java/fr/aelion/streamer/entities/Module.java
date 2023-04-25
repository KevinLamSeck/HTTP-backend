package fr.aelion.streamer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Embeddable
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String objective;

    @ManyToOne()
    private Member creator;

    @OneToMany(mappedBy = "module")
    private List<CourseToModule> courses;

    @OneToMany(mappedBy = "module")
    private List<ModuleToMedia> medias;
}
