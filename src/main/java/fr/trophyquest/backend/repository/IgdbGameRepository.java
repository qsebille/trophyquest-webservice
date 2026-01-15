package fr.trophyquest.backend.repository;

import fr.trophyquest.backend.domain.entity.IgdbGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IgdbGameRepository extends JpaRepository<IgdbGame, Long> {
}
