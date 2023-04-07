package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.FullCourseDto;
import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import fr.aelion.streamer.entities.*;
import fr.aelion.streamer.entities.Module;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertDtoService {
    @Autowired
    ModelMapper modelMapper;

    public FullCourseDto convertCourseToDto(Course course) {
        FullCourseDto fullCourseDto = new FullCourseDto();
        fullCourseDto = modelMapper.map(course, FullCourseDto.class);//renseigne les valeurs de base de cours

        List<ModuleDto> mdtos = this.getModuleListDto(course.getModules());

//        List<ModuleDto> mdtos = this.getModuleList(course.getModules()).stream().map(m -> {//gere la recuperation des modules du cours
//
//            ModuleDto mdto = modelMapper.map(m, ModuleDto.class);//
//            List<Media> medias = this.getMediaList(m.getMedias());
//            List<MediaDto> mediaDtos = medias.stream().map(med -> {
//                return modelMapper.map(med, MediaDto.class);
//            }).toList();
//            mdto.setMedias(mediaDtos);
//            return mdto;
//        }).toList();
        fullCourseDto.setModules(mdtos);


        return fullCourseDto;
    }

    public List<ModuleDto> getModuleListDto(List<CourseToModule> cTmList) {//fonction qui renvois directement tout les modules plutot que la table de jointure
        List<ModuleDto> newModulesList = new ArrayList<>();
        for (CourseToModule courseToModule : cTmList) {

            Module mod = courseToModule.getModule();//obtiens le module
            ModuleDto newModule = modelMapper.map(mod,ModuleDto.class);//le transforme en DTO avec les données de bases
            newModule.setOrder(courseToModule.getOrderModule());//ajoute l'order
            newModule.setMedias(getMediaListDto(mod.getMedias()));//ajoute la liste des medias (voir getMedialistDto())

            newModulesList.add(newModule);
        }
        return newModulesList;

    }

    public List<MediaDto> getMediaListDto(List<ModuleToMedia> mTmList) {//fonction qui renvois directement tout les medias plutot que la table de jointure
        List<MediaDto> newMediaList = new ArrayList<>();
        for (ModuleToMedia moduleToMedia : mTmList) {
            Media media = moduleToMedia.getMedia();//obtiens le media
            MediaDto newMedia = modelMapper.map(media,MediaDto.class);//le transforme en DTO avec les données de bases
            newMedia.setOrder(moduleToMedia.getOrderMedia());//ajoute l'order

            newMediaList.add(newMedia);
        }
        return newMediaList;
    }
}
