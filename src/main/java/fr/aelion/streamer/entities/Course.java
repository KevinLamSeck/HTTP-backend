package fr.aelion.streamer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
@Embeddable
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String title;

    //@Value("${false}")
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean published = false;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String objective;

    @ManyToOne()
    private Member creator;

    @OneToMany(mappedBy = "course")
    private List<CourseToModule> modules = new ArrayList<CourseToModule>();

    public Course() {
        this.createdAt = LocalDate.now();
    }
}
