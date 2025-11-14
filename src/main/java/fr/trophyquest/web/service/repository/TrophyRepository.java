package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.model.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrophyRepository extends JpaRepository<Trophy, UUID> {

    List<Trophy> findByGameId(UUID gameId);

}
