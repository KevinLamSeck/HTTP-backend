package fr.aelion.streamer.repositories;

import fr.aelion.streamer.dto.SimpleMemberProjection;
import fr.aelion.streamer.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Member findByEmail(String email);

    public Member findByLogin(String login);

    @Query("SELECT s.id id, s.lastName lastName, s.firstName firstName, s.email email FROM Member s")
    public List<SimpleMemberProjection> getSimpleMembers();

    @CrossOrigin
    Optional<Member> findByLoginAndPassword(String login, String password);

    Optional<Member> findByLoginAndEmail(String login, String email);
}
