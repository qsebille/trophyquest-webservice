package fr.trophyquest.backend.domain.entity;

import fr.trophyquest.backend.domain.entity.embedded.PlayedTrophySetId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "played_trophy_set")
@Data
public class PlayedTrophySet {

    @EmbeddedId
    private PlayedTrophySetId id;

    private Instant lastPlayedAt;

    @MapsId("playerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @MapsId("trophySetId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trophy_set_id", nullable = false)
    private TrophySet trophySet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayedTrophySet that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
