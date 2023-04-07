package fr.aelion.streamer.dto;

import fr.aelion.streamer.entities.Module;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseToModuleDto {

    @Column(nullable = false)
    private Module module;
    @Column(nullable = false)
    private Integer order;
}

