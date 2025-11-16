package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.entity.PsnUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<PsnUser, UUID> {
    UUID id(UUID id);
}
