package fr.trophyquest.web.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "trophy")
@Data
public class Trophy {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Game game;

    private int rank;

    private String title;

    private String description;

    private String trophyType;

    private boolean isHidden;

    private String iconUrl;

}