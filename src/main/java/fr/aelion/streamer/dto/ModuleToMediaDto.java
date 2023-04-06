package fr.aelion.streamer.dto;

import fr.aelion.streamer.entities.Media;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleToMediaDto {
    @Column(nullable = false)
    private Media media;
    @Column(nullable = false)
    private Integer order;
}
