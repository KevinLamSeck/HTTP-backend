package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.conceptorDtos.MediaDto;
import fr.aelion.streamer.dto.conceptorDtos.StudentDto;
import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Student;
import fr.aelion.streamer.repositories.MediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public List<MediaDto> findAll() {
        List<Media> medias = repository.findAll();
        List<MediaDto> mediasDto = medias.stream().map(s->{
            MediaDto mediaDto = modelMapper.map(s, MediaDto.class);
            return mediaDto;
        }).toList();
        return mediasDto;
    }

    public Media findOne(int id) {
        return repository.findById(id)
                .map(s -> s)
                .orElseThrow();
    }
}
