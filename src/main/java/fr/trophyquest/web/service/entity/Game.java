package fr.trophyquest.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "game")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String title;

    private String platforms;

    private String imageUrl;

    @OneToMany(mappedBy = "game")
    private Set<TrophyCollection> trophyCollections;

}