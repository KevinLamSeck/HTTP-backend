package fr.aelion.streamer.repositories;

import fr.aelion.streamer.entities.CourseToModule;
import fr.aelion.streamer.entities.ModuleToMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseToModuleRepository extends JpaRepository<CourseToModule, Integer> {

    @Query(
            value="SELECT s.* FROM course_to_module s WHERE s.course_id = :id",
            nativeQuery = true
    )
    public List<CourseToModule> getCourseToModuleByCourseId(@Param("id") int id);
}
