package fr.trophyquest.backend.repository;

import fr.trophyquest.backend.domain.entity.IgdbLink;
import fr.trophyquest.backend.domain.entity.embedded.IgdbLinkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IgdbLinkRepository extends JpaRepository<IgdbLink, IgdbLinkId> {
}
