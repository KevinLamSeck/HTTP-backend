package fr.aelion.streamer.entities;

import fr.aelion.streamer.serializable.CompositeKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
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


