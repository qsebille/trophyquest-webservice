package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.entity.PsnUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    List<Game> findAllByPsnUsers(Set<PsnUser> users, Pageable pageable);

}
