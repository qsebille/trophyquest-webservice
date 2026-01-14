package fr.trophyquest.backend.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "trophy_set")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TrophySet {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String title;

    private String platform;

    @Formula("coalesce(aws_image_url, image_url)")
    private String image;

    @OneToMany(mappedBy = "trophySet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Trophy> trophies = new ArrayList<>();

    @OneToMany(mappedBy = "trophySet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<IgdbCandidate> igdbCandidates = new HashSet<>();

    @ManyToMany(mappedBy = "playedTrophySets")
    private Set<Player> players = new HashSet<>();

}