package fr.aelion.streamer.dto.conceptorDtos;

import fr.aelion.streamer.dto.TypeMediaDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MediaDto {
    private int id;
    private String title;
    private String summary;
    private Float duration;
    private String totalTime;
    private LocalDate createdAt;
    private String url;
    private Integer order = null;
    private TypeMediaDto typeMedia;
}
