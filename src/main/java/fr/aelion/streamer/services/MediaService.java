package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.AddMediaDto;
import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.dto.simplerDtos.MemberDto;
import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Member;
import fr.aelion.streamer.entities.TypeMedia;
import fr.aelion.streamer.repositories.MediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private MediaRepository repository;
    @Autowired
    private MemberService memberService;

    public List<MediaDto> findAll() {
        List<Media> medias = repository.findAll();
        List<MediaDto> mediasDto = medias.stream().map(s -> {
            MediaDto mediaDto = modelMapper.map(s, MediaDto.class);
            return mediaDto;
        }).toList();
        return mediasDto;
    }

    public Optional<AddMediaDto> findOne(int id) {
        return repository.findById(id)
                .map(s -> {
                    AddMediaDto mediaDto = modelMapper.map(s, AddMediaDto.class);
                    return mediaDto;
                });
    }

    public List<MediaDto> findByCreatorID(int id) {
        List<Media> mediaFromRepo = repository.findByCreatorId(id);
        return mediaFromRepo.stream().map(s -> modelMapper.map(s, MediaDto.class)).toList();
    }


    public AddMediaDto add(AddMediaDto media) {
        var newMedia = new Media();
        newMedia.setTitle(media.getTitle());
        newMedia.setSummary(media.getSummary());
        newMedia.setDuration(media.getDuration());
        newMedia.setCreatedAt(LocalDate.now());
        newMedia.setUrl(media.getUrl());
        // Find the member by id
        MemberDto creator = memberService.findById(media.getCreator().getId());
        newMedia.setCreator(modelMapper.map(creator, Member.class));
        var typeMedia = media.getTypeMedia();
        newMedia.setTypeMedia(modelMapper.map(typeMedia, TypeMedia.class));

        newMedia = repository.save(newMedia);

        return modelMapper.map(newMedia, AddMediaDto.class);
    }

//    public AddMediaDto store(MultipartFile file) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Media media = new Media(fileName, file.getBytes());
////        media.setUrl(file.g);
//        repository.save(media);
//        return modelMapper.map(media, AddMediaDto.class);
//    }

    // Update media
    public AddMediaDto update(Media media) {
        Optional<Media> optionalMedia = repository.findById(media.getId());
        if (optionalMedia.isPresent()) {
            MemberDto creator = memberService.findById(media.getCreator().getId());
            media.setCreator(modelMapper.map(creator, Member.class));
            Media mediaSaved =  repository.save(modelMapper.map(media, Media.class));
            return modelMapper.map(mediaSaved, AddMediaDto.class);
        } else {
            throw new IllegalArgumentException("Media with ID " + media.getId() + " not found");
        }
    }


    public void delete(int id) {
        repository.deleteById(id);
    }

}
