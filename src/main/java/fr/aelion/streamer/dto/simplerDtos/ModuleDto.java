package fr.aelion.streamer.dto.simplerDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
