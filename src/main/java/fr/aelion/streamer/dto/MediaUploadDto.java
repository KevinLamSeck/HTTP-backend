package fr.aelion.streamer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaUploadDto {
    private String name;
    private String url;

    public MediaUploadDto(String name, String url) {
         this.name = name;
        this.url = url;
    }
}
