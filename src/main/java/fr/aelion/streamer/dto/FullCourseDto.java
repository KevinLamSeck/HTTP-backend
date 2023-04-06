package fr.aelion.streamer.dto;

import fr.aelion.streamer.entities.Course;
import fr.aelion.streamer.entities.CourseToModule;
import fr.aelion.streamer.entities.Module;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class FullCourseDto {
    private int id;
    private String title;
    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String objective;

    private Set<CourseToModuleDto> modules = new HashSet<>();

    public List<Module> getModuleList(){//fonction qui renvois directement tout les modules plutot que la table de jointure
        List<Module> newModulesList = new ArrayList<>();
        for (CourseToModuleDto courseToModule : this.modules)
        {
            Module mod = courseToModule.getModule();
            newModulesList.add(mod);
        }
        return newModulesList;
    }


}
