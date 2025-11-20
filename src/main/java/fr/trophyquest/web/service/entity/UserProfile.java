package fr.trophyquest.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "user_profile")
@Data
public class UserProfile {

    @Id
    private UUID id;

    private String name;

    private String avatarUrl;

}