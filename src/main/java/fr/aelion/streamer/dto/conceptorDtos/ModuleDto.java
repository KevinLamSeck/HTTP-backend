package fr.aelion.streamer.dto.conceptorDtos;

import fr.aelion.streamer.dto.ModuleToMediaDto;
import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Module;
import fr.aelion.streamer.entities.ModuleToMedia;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ModuleDto {
    private int id;
    private String name;
    private String objective;

    private List<MediaDto> medias= new ArrayList<>();

    private String totalTime;
    private Integer order = null;

    private boolean isSelected = false;



}
