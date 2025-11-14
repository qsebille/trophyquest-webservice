package fr.trophyquest.web.service.repository;

import fr.trophyquest.web.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
