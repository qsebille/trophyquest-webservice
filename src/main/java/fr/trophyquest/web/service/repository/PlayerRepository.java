package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
