package fr.trophyquest.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "player")
@Data
public class Player {

    @Id
    private UUID id;

    private String pseudo;

    private String avatarUrl;

}