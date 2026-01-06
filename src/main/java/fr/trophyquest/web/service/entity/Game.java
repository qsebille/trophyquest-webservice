package fr.trophyquest.web.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    private String platform;

    private String imageUrl;

    private String awsImageUrl;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trophy> trophies = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IgdbCandidate> igdbCandidates = new HashSet<>();

    public String getSanitizedImageUrl() {
        return Optional.ofNullable(awsImageUrl).orElse(imageUrl);
    }

}