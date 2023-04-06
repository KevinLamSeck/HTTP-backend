package fr.aelion.streamer.dto;

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

    private Set<ModuleToMediaDto> medias;

    private String totalTime;

    private boolean isSelected = false;

    public List<Media> getMediaList(){//fonction qui renvois directement tout les medias plutot que la table de jointure
        List<Media> newMediaList = new ArrayList<>();
        for (ModuleToMediaDto ModuleToMedia : this.medias)
        {
            Media media = ModuleToMedia.getMedia();
            newMediaList.add(media);
        }
        return newMediaList;
    }
}
