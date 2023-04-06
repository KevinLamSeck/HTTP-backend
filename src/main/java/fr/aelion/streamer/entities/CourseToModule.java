package fr.aelion.streamer.entities;

import fr.aelion.streamer.serializable.CompositeKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(CompositeKey.class)
public class CourseToModule {
    @Id
    @ManyToOne()
    private Course course;
    @Id
    @ManyToOne()
    private Module module;
    private Integer orderModule;
}


