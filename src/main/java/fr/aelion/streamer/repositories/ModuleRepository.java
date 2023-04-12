package fr.aelion.streamer.repositories;

import fr.aelion.streamer.dto.simplerDtos.MemberDto;
import fr.aelion.streamer.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    public Module findByNameAndObjectiveAndCreator(String name, String objective, MemberDto creator);
}
