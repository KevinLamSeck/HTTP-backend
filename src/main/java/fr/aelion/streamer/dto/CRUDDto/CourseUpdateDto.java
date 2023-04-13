package fr.aelion.streamer.dto.CRUDDto;

import fr.aelion.streamer.dto.CourseAddDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateDto extends CourseAddDto {
    private int id;
}
