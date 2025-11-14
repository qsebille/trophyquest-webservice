package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
