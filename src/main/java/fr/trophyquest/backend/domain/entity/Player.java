package fr.trophyquest.backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "player")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String pseudo;

    @Formula("coalesce(aws_avatar_url, avatar_url)")
    private String avatar;

    @ManyToMany
    @JoinTable(
            name = "played_trophy_set",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "trophy_set_id")
    )
    private List<TrophySet> playedTrophySets = new ArrayList<>();

    @OneToMany(mappedBy = "player")
    private Set<EarnedTrophy> earnedTrophies = new HashSet<>();

}