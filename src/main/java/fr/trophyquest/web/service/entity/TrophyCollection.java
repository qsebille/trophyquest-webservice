package fr.trophyquest.web.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@Table(name = "trophy_collection")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TrophyCollection {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Game game;

    private String platform;

    private String imageUrl;

}