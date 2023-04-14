package fr.aelion.streamer.repositories;

import fr.aelion.streamer.dto.simplerDtos.MediaDto;
import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Member;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Integer> {

    List<Media> findByCreatorId(int id);

}
