package fr.trophyquest.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "game")
@Data
public class Game {

    @Id
    private UUID id;

    private String title;

    private String platform;

    private String imageUrl;

}