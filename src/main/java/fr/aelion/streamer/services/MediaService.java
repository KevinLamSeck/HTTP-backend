package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.AddMediaDto;
import fr.aelion.streamer.dto.TypeMediaDto;
import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Module;
import fr.aelion.streamer.entities.ModuleToMedia;
import fr.aelion.streamer.entities.TypeMedia;
import fr.aelion.streamer.repositories.MediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private MediaRepository repository;

    public List<MediaDto> findAll() {
        List<Media> medias = repository.findAll();
        List<MediaDto> mediasDto = medias.stream().map(s -> {
            MediaDto mediaDto = modelMapper.map(s, MediaDto.class);
            return mediaDto;
        }).toList();
        return mediasDto;
    }

    public Optional<MediaDto> findOne(int id) {
        return repository.findById(id)
                .map(s -> {
                    MediaDto mediaDto = modelMapper.map(s, MediaDto.class);
                    return mediaDto;
                });
    }


    public AddMediaDto add(AddMediaDto media) {
        var newMedia = new Media();
        newMedia.setTitle(media.getTitle());
        newMedia.setSummary(media.getSummary());
        newMedia.setDuration(media.getDuration());
        newMedia.setCreatedAt(LocalDate.now());
        newMedia.setUrl(media.getUrl());
        newMedia.setCreator(media.getCreator());
        var typeMedia = media.getTypeMedia();
        newMedia.setTypeMedia(modelMapper.map(typeMedia, TypeMedia.class));

        newMedia = repository.save(newMedia);

        return modelMapper.map(newMedia, AddMediaDto.class);
    }
}
