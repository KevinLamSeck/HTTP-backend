package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.ModuleAddDto;
import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.dto.simplerDtos.MemberDto;
import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Member;
import fr.aelion.streamer.entities.Module;
import fr.aelion.streamer.entities.ModuleToMedia;
import fr.aelion.streamer.repositories.MediaRepository;
import fr.aelion.streamer.repositories.ModuleRepository;
import fr.aelion.streamer.repositories.ModuleToMediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuleService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private ModuleRepository repository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private ModuleToMediaRepository moduleToMediaRepository;
    @Autowired
    private ConvertDtoService convertDtoService;

    public List<ModuleDto> findAll() {
        List<Module> modules = repository.findAll();
        List<ModuleDto> modulesDto = modules.stream().map(s -> {
            ModuleDto moduleDto = modelMapper.map(s, ModuleDto.class);
            return moduleDto;
        }).toList();
        return modulesDto;
    }

    public ModuleDto findOne(int id) {
        return repository.findById(id)
                .map(s -> {
                    ModuleDto moduleDto = modelMapper.map(s,ModuleDto.class);
                    moduleDto.setMedias(convertDtoService.getMediaListDto(s.getMedias()));
                    return moduleDto;
                })
                .orElseThrow();
    }

    public ModuleDto add(ModuleAddDto module) {
        var newModule = new Module();
        newModule.setName(module.getName());
        newModule.setObjective(module.getObjective());
        newModule.setCreator(modelMapper.map(module.getCreator(), Member.class));
        newModule = repository.save(newModule);

        if (module.getMedias().size() > 0) {
            Module finalNewModule = newModule;
            List<ModuleToMedia> moduleMedias = new ArrayList<>();

            int i = 0;
            for (MediaDto m : module.getMedias()) {
                Media newMedia = modelMapper.map(m, Media.class);
                //creer le media
                newMedia = mediaRepository.save(newMedia);

                //creer la table lien entre le module et chaques medias
                ModuleToMedia moduleToMedia = new ModuleToMedia();
                moduleToMedia.setModule(newModule);
                moduleToMedia.setMedia(newMedia);
                moduleToMedia.setOrderMedia(i);
                moduleToMediaRepository.save(moduleToMedia);

                i++;

            }

        }
        return modelMapper.map(newModule, ModuleDto.class);
    }

}
