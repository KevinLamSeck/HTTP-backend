package fr.aelion.streamer.dto;

import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.dto.simplerDtos.MemberDto;
import fr.aelion.streamer.entities.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModuleAddDto {
    private String name;
    private String objective;
    private MemberDto creator;
    private List<MediaDto> medias;

}

