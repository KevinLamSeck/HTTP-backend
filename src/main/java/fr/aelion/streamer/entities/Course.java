package fr.aelion.streamer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="course")
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
    @Column(columnDefinition = "boolean default false",nullable = false)
    private Boolean published=false;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String objective;

    @ManyToOne()
    private Student creator;

    @OneToMany(mappedBy = "course")
    private List<CourseToModule> modules = new ArrayList<CourseToModule>();

    public Course() {
        this.createdAt = LocalDate.now();
    }
}
