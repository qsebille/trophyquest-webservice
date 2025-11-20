package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrophyRepository extends JpaRepository<Trophy, UUID> {
}
