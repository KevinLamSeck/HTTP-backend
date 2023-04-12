package fr.aelion.streamer.repositories;

import fr.aelion.streamer.entities.ModuleToMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleToMediaRepository extends JpaRepository<ModuleToMedia, Integer> {


    @Query(
            value="SELECT s.* FROM module_to_media s WHERE s.module_id = :id",
            nativeQuery = true
    )
    public List<ModuleToMedia> getModulesToMediasByModuleId(@Param("id") int id);

}
