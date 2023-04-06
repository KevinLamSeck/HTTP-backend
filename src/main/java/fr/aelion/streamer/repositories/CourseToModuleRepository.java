package fr.aelion.streamer.repositories;

import fr.aelion.streamer.entities.Course;
import fr.aelion.streamer.entities.CourseToModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseToModuleRepository extends JpaRepository<CourseToModule, Integer> {
}
